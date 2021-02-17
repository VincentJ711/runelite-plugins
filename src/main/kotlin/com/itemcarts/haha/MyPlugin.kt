package com.itemcarts.haha

import com.itemcarts.ItemCartsConfig
import com.itemcarts.logger
import net.runelite.api.Client
import net.runelite.client.callback.ClientThread
import net.runelite.client.game.ItemManager
import net.runelite.client.plugins.Plugin
import net.runelite.client.plugins.PluginDescriptor
import net.runelite.client.ui.ClientToolbar
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

  @Inject
  private lateinit var config: ItemCartsConfig

  @Inject
  private lateinit var itemManager: ItemManager

  @Inject
  private lateinit var clientThread: ClientThread

  private val lgr = logger()
  private val membersObject = "Members object"


}