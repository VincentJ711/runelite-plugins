package com.itemcarts.haha.ui.cartsview

import com.itemcarts.haha.Cart
import com.itemcarts.haha.ui.Destroyable
import net.runelite.client.ui.ColorScheme
import java.awt.BorderLayout
import java.awt.Color
import java.awt.GridBagLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingConstants
import javax.swing.border.EmptyBorder

class CartComponent(
  private val cart: Cart,
  private val expandedCarts: MutableSet<String>
) : JPanel(BorderLayout()), Destroyable {
  private val expandBtn = ExpandCartButton(expandedCarts.contains(cart.uid)) {
    afterExpandToggle()
  }

  private val editBtn = EditCartButton {
    TODO()
  }

  private val deleteBtn = DeleteCartButton {
    TODO()
  }

  init {
    val topPanel = JPanel(BorderLayout())
    val middlePanel = JPanel(GridBagLayout()) // TODO
    val bottomPanel = JPanel(BorderLayout())
    val cartNameLabel = JLabel(cart.name, SwingConstants.LEFT)
    cartNameLabel.isOpaque = true
    cartNameLabel.background = ColorScheme.BRAND_ORANGE

    topPanel.add(expandBtn, BorderLayout.WEST)
    topPanel.add(cartNameLabel, BorderLayout.CENTER)

    background = Color.RED
    border = EmptyBorder(8, 8, 8, 8)
    add(topPanel, BorderLayout.NORTH)
  }

  private fun afterExpandToggle() {
    if (expandBtn.expanded) {
      expandedCarts.add(cart.uid)
    } else {
      expandedCarts.remove(cart.uid)
    }
  }

  override fun onBeforeDestroy() {
    deleteBtn.onBeforeDestroy()
    editBtn.onBeforeDestroy()
    expandBtn.onBeforeDestroy()
  }
}