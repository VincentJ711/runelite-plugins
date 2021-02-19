package com.itemcarts.haha.ui

import net.runelite.client.ui.PluginPanel
import java.awt.Color
import javax.inject.Singleton

@Singleton
class ItemCartsPluginPanel : PluginPanel() {
  init {
    background = Color.BLUE
  }
}