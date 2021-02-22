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

    modelManager.setCarts((1 until 100).map {
      if (it % 2 == 0) {
        RawCart(
          "bravo", listOf(
            RawCartItem("Abyssal Whip", 0, 10),
            RawCartItem("Zammy Hasta   asdfasdfa asdfasdf sdf", 1, 1234345)
          )
        )
      } else {
        RawCart(
          "alpha", listOf(
            RawCartItem("Tinderbox", 1, 0)
          )
        )
      }
    })

    // modelManager.setCarts(
    //   listOf(
    //     RawCart(
    //       "bravo", listOf(
    //         RawCartItem("Abyssal Whip", 0, 10),
    //         RawCartItem("Zammy Hasta", 1, 1234345)
    //       )
    //     ),
    //     RawCart(
    //       "alpha", listOf(
    //         RawCartItem("Tinderbox", 1, 0)
    //       )
    //     )
    //   )
    // )
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