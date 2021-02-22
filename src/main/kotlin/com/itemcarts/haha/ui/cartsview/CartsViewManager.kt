package com.itemcarts.haha.ui.cartsview

import com.itemcarts.haha.Cart
import com.itemcarts.haha.ModelManager
import com.itemcarts.haha.ontoEDT
import com.itemcarts.haha.ui.UiManager
import net.runelite.client.ui.ColorScheme
import net.runelite.client.ui.components.CustomScrollBarUI
import java.awt.BorderLayout
import java.awt.Dimension
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import javax.swing.BoxLayout
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextArea
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

@Singleton
class CartsViewManager : ICartsViewManager {
  @Inject
  private lateinit var modelManager: ModelManager

  @Inject
  // Provider breaks a circular dependency
  private lateinit var uiManager: Provider<UiManager>

  private val cartsListPanel = JPanel()
  private val expandedCarts = mutableSetOf<String>()
  private val cartComponents = mutableMapOf<String, CartPanel>()
  val rootPanel = JPanel(BorderLayout())

  init {
    val ctrlPanel = JPanel()
    ctrlPanel.background = ColorScheme.DARK_GRAY_COLOR
    ctrlPanel.add(JTextArea())

    val cartsListWrapper = JPanel(BorderLayout())
    val filler = JPanel()
    val scrollPane = JScrollPane(cartsListWrapper)

    filler.background = ColorScheme.DARK_GRAY_COLOR
    scrollPane.verticalScrollBar.preferredSize = Dimension(8, 0)
    scrollPane.verticalScrollBar.ui = CustomScrollBarUI()
    scrollPane.verticalScrollBar.unitIncrement = 16
    scrollPane.border = EmptyBorder(0, 0, 0, 0)

    cartsListPanel.background = ColorScheme.DARK_GRAY_COLOR
    cartsListPanel.border = EmptyBorder(0, 4, 0, 4)
    cartsListPanel.layout = BoxLayout(cartsListPanel, BoxLayout.Y_AXIS)

    cartsListWrapper.add(cartsListPanel, BorderLayout.NORTH)
    cartsListWrapper.add(filler, BorderLayout.CENTER)
    rootPanel.add(ctrlPanel, BorderLayout.NORTH)
    rootPanel.add(scrollPane, BorderLayout.CENTER)
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
        val nextComp = renderCart(next)
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
        val comp = renderCart(cart)
        cartsListPanel.add(comp)
        cartComponents[cart.uid] = comp
      }
    }
  }

  private fun renderCart(cart: Cart) =
    CartPanel(uiManager.get(), modelManager, cart, expandedCarts)
}