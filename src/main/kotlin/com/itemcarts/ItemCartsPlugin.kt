package com.itemcarts

import com.google.inject.Provides
import net.runelite.api.Client
import net.runelite.api.GameState
import net.runelite.api.InventoryID
import net.runelite.api.Item
import net.runelite.api.events.GameStateChanged
import net.runelite.api.events.GameTick
import net.runelite.api.events.ItemContainerChanged
import net.runelite.api.widgets.WidgetInfo
import net.runelite.client.callback.ClientThread
import net.runelite.client.config.ConfigManager
import net.runelite.client.eventbus.Subscribe
import net.runelite.client.game.ItemManager
import net.runelite.client.plugins.Plugin
import net.runelite.client.plugins.PluginDescriptor
import net.runelite.client.ui.ClientToolbar
import net.runelite.client.ui.NavigationButton
import net.runelite.client.ui.PluginPanel
import net.runelite.client.util.ImageUtil
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import kotlin.math.min

@PluginDescriptor(
    name = "Item Carts",
    description = ""
)
class ItemCartsPlugin : Plugin() {
  @Inject
  private lateinit var client: Client

  @Inject
  private lateinit var clientToolbar: ClientToolbar

  @Inject
  private lateinit var config: ItemCartsConfig

  @Inject
  private lateinit var itemManager: ItemManager

  @Inject
  private lateinit var clientThread: ClientThread

  private val lgr = logger()
  private val membersObject = "Members object"
  private var persistModels = false
  private var firstStart = true
  private var reconfigCartItems = true
  private val lastBankItems = mutableMapOf<String, Array<Item?>>()
  private lateinit var modelsWriterThread: ModelsWriterThread
  private lateinit var navButton: NavigationButton
  private lateinit var viewsCtrl: ViewsCtrl
  private lateinit var modelsCtrl: IModelsCtrl

  override fun startUp() {
    if (firstStart) {
      // modelsCtrl = ModelsCtrl(
      //     "eyJhbWFzb245NDA3QGdtYWlsLmNvbSI6eyJpdGVtc09uUGxheWVyIjp7fSwiaXRlbXNJbkJhbmsiOnt9LCJjYXJ0cyI6W3sibmFtZSI6InRlc3QgY2FydDEiLCJ1aWQiOiJhc2Rmc2RmIiwidGFncyI6W10sIml0ZW1zIjpbeyJuYW1lIjoiU2hyaW1wcyIsInRhcmdldCI6MjB9XX1dfX0="
      // ) {
      modelsCtrl = ModelsCtrl(config.models()) {
        reconfigCartItems = true
        refresh()
      }
      firstStart = false
    }

    persistModels = false
    reconfigCartItems = true
    viewsCtrl = ViewsCtrl(modelsCtrl)
    navButton = createNavButton(viewsCtrl)
    clientToolbar.addNavigation(navButton)
    modelsWriterThread = ModelsWriterThread()
    modelsWriterThread.start()
  }

  private fun refresh() = ontoClientThread {
    val uname = client.username ?: return@ontoClientThread
    modelsCtrl.register(uname)

    if (bankIsOpen()) {
      val items = client.getItemContainer(InventoryID.BANK)?.items
      lastBankItems[uname] = items ?: return@ontoClientThread
      return@ontoClientThread
    }

    var model = modelsCtrl.getModel(uname) ?: return@ontoClientThread
    val biarr = lastBankItems[uname]
    val itemsInBank = if (biarr != null) mapItemsInBank(biarr) else null

    if (itemsInBank == null && !reconfigCartItems) {
      return@ontoClientThread
    }

    lastBankItems.remove(uname)
    reconfigCartItems = false

    val itemsOnPlayer = mapItemsOnPlayer(
        invy = client.getItemContainer(InventoryID.INVENTORY)?.items,
        eqpt = client.getItemContainer(InventoryID.EQUIPMENT)?.items
    )
    val ans = refreshCartItemAmounts(model)

    model = model.copy(
        carts = ans.carts,
        itemsOnPlayer = itemsOnPlayer,
        itemsInBank = itemsInBank ?: model.itemsInBank
    )

    lgr.info("notifying model/view controllers of model change...")
    modelsCtrl.setModel(uname, model)
    viewsCtrl.setModel(uname, model)
    persistModels = true
  }

  private fun bankIsOpen(): Boolean {
    val bankWig = client.getWidget(WidgetInfo.BANK_CONTAINER)
    return bankWig != null && !bankWig.isHidden
  }

  private fun mapItemsInBank(bankItems: Array<Item?>): Map<String, Long> {
    val itemsInBank = mutableMapOf<String, Long>()
    for (item in bankItems) {
      if (item != null) {
        val ic = itemManager.getItemComposition(item.id)
        // if its not a placeholder and its not a members object, add it.
        if (ic.placeholderTemplateId == -1 && ic.name != membersObject) {
          itemsInBank[ic.name] = item.quantity.toLong()
        }
      }
    }
    return itemsInBank
  }

  private fun mapItemsOnPlayer(invy: Array<Item?>?, eqpt: Array<Item?>?):
      Map<String, Long> {
    val itemsOnPlayer = mutableMapOf<String, Long>()
    val items = arrayOf(*(invy ?: emptyArray()), *(eqpt ?: emptyArray()))

    for (item in items) {
      if (item != null) {
        val name = itemManager.getItemComposition(item.id).name
        if (name != null && name != membersObject) {
          itemsOnPlayer[name] = (itemsOnPlayer[name] ?: 0) + item.quantity
        }
      }
    }
    return itemsOnPlayer
  }

  /**
   * @return a cart for each of the given models' carts where the cart item
   * amounts are updated to reflect the given models itemsOnPlayer/Bank. This
   * also returns which items and how many of them are still needed after
   * splitting amongst all the cart items with the same name. only positive
   * values have keys in this returned map.
   */
  private fun refreshCartItemAmounts(model: Model): CartsRefreshResult {
    val totalRemaining = mutableMapOf<String, Long>()
    val carts = model.carts.map { cart ->
      val items = cart.items.map { item ->
        val actualAmt = (model.itemsInBank[item.name] ?: 0) +
            (model.itemsOnPlayer[item.name] ?: 0)
        val remainingForThisItem = totalRemaining[item.name] ?: actualAmt
        val amtToUseUp = min(remainingForThisItem, item.target)
        totalRemaining[item.name] = remainingForThisItem - amtToUseUp
        item.copy(splitAmt = amtToUseUp, actualAmt = actualAmt)
      }
      cart.copy(items = items)
    }

    totalRemaining.values.removeIf { it <= 0 }
    return CartsRefreshResult(carts, totalRemaining)
  }

  private fun ontoClientThread(cb: () -> Unit) {
    if (client.isClientThread) cb() else clientThread.invoke(Runnable(cb))
  }

  private fun tryPersistingModels() = ontoClientThread {
    if (persistModels) {
      lgr.info("persisting models to config")
      persistModels = false
      config.models(modelsCtrl.encodeModels())
    }
  }

  // startup sometimes isnt called on client thread...?
  private fun createNavButton(panel: PluginPanel): NavigationButton {
    val icon = ImageUtil.loadImageResource(javaClass, "/navicon.png")
    return NavigationButton.builder()
        .tooltip("Item Carts")
        .icon(icon)
        .panel(panel)
        .build()
  }

  @Subscribe
  fun onGameTick(gameTick: GameTick) = refresh()

  @Subscribe
  fun onGameStateChanged(gameStateChanged: GameStateChanged) {
    if (gameStateChanged.gameState == GameState.LOGGED_IN) {
      reconfigCartItems = true
    }
  }

  @Subscribe
  fun onItemContainerChanged(evt: ItemContainerChanged) {
    reconfigCartItems = true
  }

  override fun shutDown() {
    tryPersistingModels()
    // viewsCtrl.onBeforeDestroy()
    clientToolbar.removeNavigation(navButton)
    modelsWriterThread.stop()
  }

  @Provides
  fun provideConfig(configManager: ConfigManager): ItemCartsConfig =
      configManager.getConfig(ItemCartsConfig::class.java)

  private data class CartsRefreshResult(
      val carts: List<Cart>,
      val remaining: Map<String, Long>
  )

  private inner class ModelsWriterThread : Runnable {
    private val thread = Thread(this)
    private var running = AtomicBoolean(false)

    fun start() {
      thread.start()
    }

    fun stop() {
      running.set(false)
    }

    override fun run() {
      running.set(true)

      while (running.get()) {
        tryPersistingModels()

        try {
          Thread.sleep(10000)
        } catch (e: InterruptedException) {
          running.set(false)
        }
      }
    }
  }
}