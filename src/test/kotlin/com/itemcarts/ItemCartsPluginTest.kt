package com.itemcarts

import net.runelite.client.RuneLite
import net.runelite.client.externalplugins.ExternalPluginManager

fun main(args: Array<String>) {
  ExternalPluginManager.loadBuiltin(ItemCartsPlugin::class.java)
  RuneLite.main(args)
}