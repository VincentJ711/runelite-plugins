package com.itemcarts.haha

import net.runelite.client.ui.FontManager
import net.runelite.client.ui.PluginPanel
import net.runelite.client.ui.skin.SubstanceRuneLiteLookAndFeel
import net.runelite.client.util.SwingUtil
import javax.swing.JFrame

private class ViewTest : JFrame() {
  val rootPanel = object : PluginPanel() {}
  val modelManager = ModelManager(rootPanel)

  init {
    title = "View Test"
    setSize(500, 900)
    defaultCloseOperation = EXIT_ON_CLOSE

    add(rootPanel)
    isVisible = true

    modelManager.setCarts(
      listOf(
        RawCart("alpha", emptyList()),
        RawCart("bravo", emptyList())
      )
    )

    Thread {
      val waitTime = 3000L

      println("adding 2 carts")
      Thread.sleep(waitTime)
      modelManager.addCarts(
        listOf(
          RawCart("charlie", emptyList()),
          RawCart("delta", emptyList())
        )
      )

      println("updating carts at index 0 and 2")
      assert(modelManager.carts.size > 2)
      Thread.sleep(waitTime)
      val cart0 = modelManager.carts[0]
      val cart2 = modelManager.carts[2]
      modelManager.updateCarts(
        listOf(
          cart2.copy(name = "${cart2.name} ${cart2.name}"),
          cart0.copy(name = "${cart0.name} ${cart0.name}")
        )
      )

      println("removing cart at index 1")
      modelManager.carts.getOrNull(1)?.let {
        Thread.sleep(waitTime)
        modelManager.removeCart(it.uid)
      }

      println("resetting carts")
      Thread.sleep(waitTime)
      modelManager.setCarts((1 until 100).map {
        RawCart("cart #${it}", emptyList())
      })
    }.start()
  }
}

fun main() = ontoEDT {
  SwingUtil.setupDefaults()
  SwingUtil.setTheme(SubstanceRuneLiteLookAndFeel())
  SwingUtil.setFont(FontManager.getRunescapeFont())
  ViewTest()
}