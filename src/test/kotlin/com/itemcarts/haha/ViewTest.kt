package com.itemcarts.haha

import net.runelite.client.ui.FontManager
import net.runelite.client.ui.PluginPanel
import net.runelite.client.ui.skin.SubstanceRuneLiteLookAndFeel
import net.runelite.client.util.SwingUtil
import javax.swing.JFrame

private class ViewTest : JFrame() {
  val cartb = RawCart("b", emptyList())
  val rootPanel = object : PluginPanel() {}
  val modelManager = ModelManager(rootPanel)

  init {
    title = "View Test"
    setSize(500, 900)
    setLocationRelativeTo(null)
    defaultCloseOperation = EXIT_ON_CLOSE

    modelManager.setCarts(
      listOf(
        RawCart("a", emptyList()),
        cartb
      )
    )

    add(rootPanel)
  }
}

fun main() = ontoEDT {
  SwingUtil.setupDefaults()
  SwingUtil.setTheme(SubstanceRuneLiteLookAndFeel())
  SwingUtil.setFont(FontManager.getRunescapeFont())
  val frame = ViewTest()
  frame.isVisible = true

  Thread {
    println(Thread.currentThread())
    Thread.sleep(1000)
    val xxx = (1 until 4).map {
      RawCart("cart #${it}", emptyList())
    }
    frame.modelManager.setCarts(xxx)
    // frame.modelManager.addCarts(
    //   listOf(
    //     Cart("alpha", emptyList(), false)
    //   )
    // )
    // frame.modelManager.removeCart(frame.cartb)
    // frame.modelManager.updateCart(
    //   frame.cartb,
    //   frame.cartb.copy(name = "was b!!")
    // )
  }.start()
}