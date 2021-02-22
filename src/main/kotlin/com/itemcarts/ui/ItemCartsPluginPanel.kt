package com.itemcarts.ui

import net.runelite.client.ui.PluginPanel
import java.awt.BorderLayout
import javax.inject.Singleton

@Singleton
class ItemCartsPluginPanel : PluginPanel(false) {
  init {
    layout = BorderLayout()
  }
}