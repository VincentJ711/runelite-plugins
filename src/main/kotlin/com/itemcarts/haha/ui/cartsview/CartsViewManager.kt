package com.itemcarts.haha.ui.cartsview

import com.itemcarts.haha.Cart
import com.itemcarts.haha.ontoEDT
import com.itemcarts.haha.ui.UiManager
import net.runelite.client.ui.ColorScheme
import java.awt.BorderLayout
import javax.swing.BoxLayout
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.border.EmptyBorder

interface ICartsViewManager {
  /** updates the carts view to replace any existing carts with these. */
  fun setCarts(carts: Iterable<Cart>)

  /** updates the carts view to reflect the changes for the given carts. */
  fun updateCarts(carts: Iterable<Cart>)

  /** updates the carts view to reflect this removal request. */
  fun removeCart(uid: String)

  /** updates the carts view to reflect the addition of the given carts. */
  fun addCarts(carts: Iterable<Cart>)
}

class CartsViewManager : ICartsViewManager {
  lateinit var uiManager: UiManager
  private val cartsListPanel = JPanel()
  private val expandedCarts = mutableSetOf<String>()
  private val cartComponents =
    mutableMapOf<String, CartPanel>()
  val rootPanel = JPanel()

  init {
    val scrollPaneChild = JPanel(BorderLayout())
    val scrollPane = JScrollPane(scrollPaneChild)
    val dummyPanel = JPanel()

    cartsListPanel.background = ColorScheme.DARK_GRAY_COLOR
    cartsListPanel.border = EmptyBorder(8, 0, 8, 0)
    cartsListPanel.layout = BoxLayout(cartsListPanel, BoxLayout.Y_AXIS)

    scrollPaneChild.add(cartsListPanel, BorderLayout.NORTH)
    scrollPaneChild.add(dummyPanel, BorderLayout.CENTER)

    rootPanel.layout = BoxLayout(rootPanel, BoxLayout.Y_AXIS)
    rootPanel.add(scrollPane)
  }

  override fun setCarts(carts: Iterable<Cart>) = ontoEDT {
    for ((_, comp) in cartComponents) {
      comp.onBeforeDestroy()
    }
    cartComponents.clear()
    cartsListPanel.removeAll()
    addCarts(carts)
  }

  override fun updateCarts(carts: Iterable<Cart>) = ontoEDT {
    carts.forEach { next ->
      val currComp = cartComponents[next.uid] ?: return@forEach
      val index = cartsListPanel.components.indexOfFirst { it == currComp }

      if (index != -1) {
        val nextComp = CartPanel(next, expandedCarts)
        currComp.onBeforeDestroy()
        cartComponents[next.uid] = nextComp
        cartsListPanel.remove(index)
        cartsListPanel.add(nextComp, index)
      }
    }
  }

  override fun removeCart(uid: String) = ontoEDT {
    val comp = cartComponents[uid] ?: return@ontoEDT
    comp.onBeforeDestroy()
    cartComponents.remove(uid)
    cartsListPanel.remove(comp)
  }

  override fun addCarts(carts: Iterable<Cart>) = ontoEDT {
    for (cart in carts) {
      if (!cartComponents.containsKey(cart.uid)) {
        val comp = CartPanel(cart, expandedCarts)
        cartsListPanel.add(comp)
        cartComponents[cart.uid] = comp
      }
    }
  }
}