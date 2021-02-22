package com.itemcarts.ui.cartsview

import com.itemcarts.Cart
import com.itemcarts.IModelManager
import com.itemcarts.ui.Destroyable
import com.itemcarts.ui.UiManager
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

class CartPanel(
  private val uiManager: UiManager,
  private val modelManager: IModelManager,
  private val cart: Cart,
  private val expandedCarts: MutableSet<String>
) : JPanel(BorderLayout()), Destroyable {
  private val btmPanel = JPanel(BorderLayout())
  private val statusBtn = StatusButton(cart.completed)
  private val cartNameBtn = CartNameButton(cart.name) { toggleExpansion() }
  private val editBtn = EditCartButton { uiManager.goToEditCartView(cart) }
  private val deleteBtn = DeleteCartButton { modelManager.removeCart(cart.uid) }

  init {
    val topPanel = JPanel(BorderLayout())
    val cartItemsPanel = CartItemsPanel(cart)
    val ctrlPanel = JPanel(BorderLayout())

    border = EmptyBorder(0, 0, 4, 0)

    topPanel.add(cartNameBtn, BorderLayout.CENTER)
    topPanel.add(statusBtn, BorderLayout.EAST)
    add(topPanel, BorderLayout.NORTH)

    btmPanel.add(cartItemsPanel, BorderLayout.NORTH)

    ctrlPanel.add(deleteBtn, BorderLayout.WEST)
    ctrlPanel.add(JPanel(), BorderLayout.CENTER)
    ctrlPanel.add(editBtn, BorderLayout.EAST)
    btmPanel.add(ctrlPanel, BorderLayout.SOUTH)

    btmPanel.border = EmptyBorder(8, 0, 0, 0)
    btmPanel.isVisible = expandedCarts.contains(cart.uid)
    add(btmPanel, BorderLayout.SOUTH)
  }

  private fun toggleExpansion() {
    if (expandedCarts.contains(cart.uid)) {
      expandedCarts.remove(cart.uid)
      btmPanel.isVisible = false
    } else {
      expandedCarts.add(cart.uid)
      btmPanel.isVisible = true
    }
  }

  override fun onBeforeDestroy() {
    cartNameBtn.onBeforeDestroy()
    statusBtn.onBeforeDestroy()
    deleteBtn.onBeforeDestroy()
    editBtn.onBeforeDestroy()
  }
}