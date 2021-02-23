package com.itemcarts

import com.itemcarts.ui.ItemCartsPluginPanel
import net.runelite.api.Client
import net.runelite.client.callback.ClientThread
import net.runelite.client.game.ItemManager
import net.runelite.client.plugins.Plugin
import net.runelite.client.plugins.PluginDescriptor
import net.runelite.client.ui.ClientToolbar
import net.runelite.client.ui.NavigationButton
import net.runelite.client.util.ImageUtil
import javax.inject.Inject

@PluginDescriptor(
  name = "Item Carts",
  description = ""
)
class ItemCartsPlugin : Plugin() {
  @Inject
  private lateinit var client: Client

  @Inject
  private lateinit var clientToolbar: ClientToolbar

  // @Inject
  // private lateinit var config: ItemCartsConfig

  @Inject
  private lateinit var itemManager: ItemManager

  @Inject
  private lateinit var clientThread: ClientThread

  @Inject
  private lateinit var itemCartsPluginPanel: ItemCartsPluginPanel

  @Inject
  private lateinit var modelManager: ModelManager

  private lateinit var navButton: NavigationButton

  override fun shutDown() {
    clientToolbar.removeNavigation(navButton)
  }

  override fun startUp() {
    navButton = createNavButton()
    clientToolbar.addNavigation(navButton)

    modelManager.setCarts((1 until 3).map {
      RawCart(
        "cart #${it}", (1 until 100).map { n ->
          RawCartItem("$n ok wowowowoowowowowoowow wowow", 1, 1)
        }
      )
    })
  }

  private fun createNavButton(): NavigationButton {
    val icon = ImageUtil.loadImageResource(javaClass, "/navicon.png")
    return NavigationButton.builder()
      .tooltip("Item Carts")
      .icon(icon)
      .panel(itemCartsPluginPanel)
      .build()
  }
}