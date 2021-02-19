package com.itemcarts.haha.ui.cartsview

import com.itemcarts.haha.Cart
import com.itemcarts.haha.ui.Destroyable
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

class CartPanel(
  private val cart: Cart,
  private val expandedCarts: MutableSet<String>
) : JPanel(BorderLayout()), Destroyable {
  private val btmPanel = JPanel(BorderLayout())
  private val statusBtn = StatusButton(cart.completed)
  private val cartNameBtn = CartNameButton(cart.name) { toggleExpansion() }
  private val editBtn = EditCartButton {
    TODO()
  }
  private val deleteBtn = DeleteCartButton {
    TODO()
  }

  init {
    val topPanel = JPanel(BorderLayout())
    val cartItemsPanel = CartItemsPanel(cart)
    val ctrlPanel = JPanel(BorderLayout())

    border = EmptyBorder(0, 0, 8, 0)

    topPanel.add(cartNameBtn, BorderLayout.CENTER)
    topPanel.add(statusBtn, BorderLayout.EAST)
    add(topPanel, BorderLayout.NORTH)

    btmPanel.add(cartItemsPanel, BorderLayout.NORTH)

    ctrlPanel.add(editBtn, BorderLayout.WEST)
    ctrlPanel.add(JPanel(), BorderLayout.CENTER)
    ctrlPanel.add(deleteBtn, BorderLayout.EAST)
    btmPanel.add(ctrlPanel, BorderLayout.SOUTH)

    btmPanel.border = EmptyBorder(8, 0, 0, 0)
    btmPanel.isVisible = true // expandedCarts.contains(cart.uid) TODO
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