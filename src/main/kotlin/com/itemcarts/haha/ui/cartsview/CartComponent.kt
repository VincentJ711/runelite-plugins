package com.itemcarts.haha.ui.cartsview

import com.itemcarts.haha.Cart
import com.itemcarts.haha.ui.Destroyable
import com.itemcarts.haha.ui.UiManager
import com.itemcarts.haha.ui.shared.LabelButton
import com.itemcarts.haha.ui.shared.LabelButtonOpts
import com.itemcarts.haha.ui.shared.TEXT_PRIMARY
import com.itemcarts.haha.ui.shared.TEXT_SECONDARY
import net.runelite.client.ui.ColorScheme
import java.awt.BorderLayout
import java.awt.GridBagLayout
import javax.swing.BorderFactory
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingConstants
import javax.swing.border.EmptyBorder

class CartComponent(
  private val cart: Cart,
  private val uiManager: UiManager,
  private val expandedCarts: MutableSet<String>
) : JPanel(BorderLayout()), Destroyable {
  private val btmPanel = JPanel(BorderLayout())
  private val statusBtn = StatusButton(cart.completed)
  private val cartNameLabel = LabelButton(
    LabelButtonOpts(
      text = cart.name,
      textColor = TEXT_SECONDARY,
      textHoverColor = TEXT_PRIMARY,
      bgColor = ColorScheme.DARKER_GRAY_COLOR,
      bgHoverColor = ColorScheme.DARKER_GRAY_HOVER_COLOR,
      callback = { toggleExpansion() }
    )
  )
  private val editBtn = EditCartButton {
    TODO()
  }
  private val deleteBtn = DeleteCartButton {
    TODO()
  }

  init {
    val topPanel = JPanel(BorderLayout())
    val detailPanel = JPanel(GridBagLayout()) // TODO
    val ctrlPanel = JPanel(BorderLayout())

    border = EmptyBorder(0, 0, 8, 0)

    cartNameLabel.horizontalAlignment = SwingConstants.LEFT
    cartNameLabel.border = EmptyBorder(0, 8, 0, 8)
    topPanel.add(cartNameLabel, BorderLayout.CENTER)
    topPanel.add(statusBtn, BorderLayout.EAST)
    add(topPanel, BorderLayout.NORTH)

    val dum = JLabel("hello")
    dum.foreground = TEXT_SECONDARY
    detailPanel.add(dum)
    btmPanel.add(detailPanel, BorderLayout.NORTH)

    ctrlPanel.add(deleteBtn, BorderLayout.WEST)
    ctrlPanel.add(JPanel(), BorderLayout.CENTER)
    ctrlPanel.add(editBtn, BorderLayout.EAST)
    btmPanel.add(ctrlPanel, BorderLayout.SOUTH)

    btmPanel.border = EmptyBorder(8, 0, 8, 0)
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
    cartNameLabel.onBeforeDestroy()
    statusBtn.onBeforeDestroy()
    deleteBtn.onBeforeDestroy()
    editBtn.onBeforeDestroy()
  }
}