package com.itemcarts.haha

import net.runelite.client.ui.ColorScheme
import java.awt.*
import javax.swing.*
import javax.swing.border.EmptyBorder

enum class View {
  CARTS,
  SUMMARY,
  EDIT_CART
}

interface IViewManager {
  fun setView(view: View)
  fun setCarts(carts: Iterable<Cart>)
  fun updateCarts(carts: Iterable<Cart>)
  fun removeCart(uid: String)
  fun addCarts(carts: Iterable<Cart>)
}

class ViewManager(
  private val modelManager: ModelManager,
  private val rootPanel: Container
) : IViewManager {
  private val cartsView = JPanel()
  private val summaryView = JPanel()
  private val editCartView = JPanel()
  private val cartsListPanel = JPanel()
  private val cartComponents = mutableMapOf<String, Component>()

  init {
    setView(View.CARTS)
  }

  // setup the carts view
  init {
    val scrollPaneChild = JPanel(BorderLayout())
    val scrollPane = JScrollPane(scrollPaneChild)
    val dummyPanel = JPanel()

    cartsListPanel.background = ColorScheme.LIGHT_GRAY_COLOR
    cartsListPanel.border = EmptyBorder(8, 8, 8, 8)
    cartsListPanel.layout = GridLayout(0, 1, 0, 8)

    scrollPaneChild.add(cartsListPanel, BorderLayout.NORTH)
    scrollPaneChild.add(dummyPanel, BorderLayout.SOUTH)

    cartsView.layout = BoxLayout(cartsView, BoxLayout.Y_AXIS)
    cartsView.add(scrollPane)
    rootPanel.add(cartsView)
  }

  // setup the summaryView
  init {
    summaryView.layout = BoxLayout(summaryView, BoxLayout.Y_AXIS)
    rootPanel.add(summaryView)
  }

  // setup the edit cart view
  init {
    editCartView.layout = BoxLayout(editCartView, BoxLayout.Y_AXIS)
    rootPanel.add(editCartView)
  }

  override fun setView(view: View) = ontoEDT {
    cartsView.isVisible = view == View.CARTS
    summaryView.isVisible = view == View.SUMMARY
    editCartView.isVisible = view == View.EDIT_CART
    refresh()
  }

  override fun setCarts(carts: Iterable<Cart>) = ontoEDT {
    cartsListPanel.removeAll()
    cartComponents.clear()
    addCarts(carts)
  }

  override fun updateCarts(carts: Iterable<Cart>) = ontoEDT {
    carts.forEach { next ->
      val currComp = cartComponents[next.uid] ?: return@forEach
      val index = cartsListPanel.components.indexOfFirst { it == currComp }

      if (index != -1) {
        val nextComp = renderCart(next)
        cartComponents[next.uid] = nextComp
        cartsListPanel.remove(index)
        cartsListPanel.add(nextComp, index)
      }
    }

    refresh()
    updateSummary()
  }

  override fun removeCart(uid: String) = ontoEDT {
    val comp = cartComponents[uid] ?: return@ontoEDT
    cartsListPanel.remove(comp)
    cartComponents.remove(uid)
    refresh()
    updateSummary()
  }

  override fun addCarts(carts: Iterable<Cart>) = ontoEDT {
    for (cart in carts) {
      if (!cartComponents.containsKey(cart.uid)) {
        val comp = renderCart(cart)
        cartsListPanel.add(comp)
        cartComponents[cart.uid] = comp
      }
    }
    refresh()
    updateSummary()
  }

  private fun refresh() {
    rootPanel.validate()
    rootPanel.repaint()
  }

  /** refreshes the summary view with latest summary */
  private fun updateSummary() {
    // TODO
    // ...
    // refresh(summaryListPane)
  }

  private fun renderCart(cart: Cart): Component {
    val panel = JPanel(BorderLayout())
    val label = JLabel(cart.name)
    panel.background = ColorScheme.DARKER_GRAY_COLOR
    panel.border = EmptyBorder(8, 8, 8, 8)
    panel.add(label)
    return panel
  }
}