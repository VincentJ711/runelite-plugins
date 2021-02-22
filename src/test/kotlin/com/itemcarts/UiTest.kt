package com.itemcarts.ui

import com.google.inject.Guice
import com.itemcarts.ModelManager
import com.itemcarts.RawCart
import com.itemcarts.RawCartItem
import com.itemcarts.ontoEDT
import net.runelite.client.ui.FontManager
import net.runelite.client.ui.skin.SubstanceRuneLiteLookAndFeel
import net.runelite.client.util.SwingUtil
import org.pushingpixels.substance.internal.SubstanceSynapse
import javax.inject.Inject
import javax.inject.Singleton
import javax.swing.JFrame

@Singleton
private class UiTest : JFrame() {
  @Inject
  lateinit var rootPanel: ItemCartsPluginPanel

  @Inject
  private lateinit var modelManager: ModelManager

  fun start() {
    title = "View Test"
    setSize(500, 900)
    defaultCloseOperation = EXIT_ON_CLOSE

    add(rootPanel)
    isVisible = true

    modelManager.setCarts(
      listOf(
        RawCart(
          "bravo", listOf(
            RawCartItem("Abyssal Whip", 0, 10),
            RawCartItem("Zammy Hasta", 1, 1234345)
          )
        ),
        RawCart(
          "alpha", listOf(
            RawCartItem("Tinderbox", 1, 0)
          )
        )
      )
    )

    Thread {
      val waitTime = 500L

      println("adding items on player/inbank")
      Thread.sleep(waitTime)
      modelManager.updateItems(
        mutableMapOf(), mapOf(
          "Abyssal Whip" to 1234L,
          "Tinderbox" to 1L
        )
      )

      // println("adding 2 carts")
      // Thread.sleep(waitTime)
      // modelManager.addCarts(
      //   listOf(
      //     RawCart("charlie", emptyList()),
      //     RawCart("delta", emptyList())
      //   )
      // )
      //
      // println("updating carts at index 0 and 2")
      // assert(modelManager.carts.size > 2)
      // Thread.sleep(waitTime)
      // val cart0 = modelManager.carts[0]
      // val cart2 = modelManager.carts[2]
      // modelManager.updateCarts(
      //   listOf(
      //     cart2.copy(name = "${cart2.name} ${cart2.name}"),
      //     cart0.copy(name = "${cart0.name} ${cart0.name}")
      //   )
      // )
      //
      // println("removing cart at index 1")
      // modelManager.carts.getOrNull(1)?.let {
      //   Thread.sleep(waitTime)
      //   modelManager.removeCart(it.uid)
      // }

      println("resetting carts")
      Thread.sleep(waitTime)
      modelManager.setCarts((1 until 100).map {
        RawCart(
          "cart #${it}", listOf(
            RawCartItem("Abyssal Whip", 0, 10),
            RawCartItem("Zammy Hasta", 1, 1234345)
          )
        )
      })
      // println(modelManager.uiManager)
    }.start()
  }
}

fun main() = ontoEDT {
  SwingUtil.setupDefaults()
  SwingUtil.setTheme(SubstanceRuneLiteLookAndFeel())
  SwingUtil.setFont(FontManager.getRunescapeFont())

  val injector = Guice.createInjector()
  val frame = injector.getInstance(UiTest::class.java)
  // the above theme screws up the coloring... this make sure colors are true...
  frame.rootPanel.putClientProperty(SubstanceSynapse.COLORIZATION_FACTOR, 1.0)

  frame.start()
}