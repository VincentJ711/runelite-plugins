package com.itemcarts.haha

import com.itemcarts.haha.ui.ItemCartsPluginPanel
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
  name = "My plugin",
  description = ""
)
class MyPlugin : Plugin() {
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

    modelManager.setCarts((1 until 30).map {
      RawCart(
        "cart #${it}", listOf(
          RawCartItem("Abyssal Whip", 0, 10),
          RawCartItem("this is a very long item name", 1, 1234345)
        )
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