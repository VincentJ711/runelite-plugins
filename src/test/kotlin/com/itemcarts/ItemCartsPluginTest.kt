package com.itemcarts

import com.itemcarts.haha.MyPlugin
import net.runelite.client.RuneLite
import net.runelite.client.externalplugins.ExternalPluginManager

fun main(args: Array<String>) {
  ExternalPluginManager.loadBuiltin(MyPlugin::class.java)
  RuneLite.main(args)
}