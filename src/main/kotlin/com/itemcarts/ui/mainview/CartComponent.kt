package com.itemcarts.ui.mainview

import com.itemcarts.Cart
import com.itemcarts.IViewsCtrl
import com.itemcarts.ui.Destroyable
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Font
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.BorderFactory
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

class CartComponent(
    private var cart: Cart,
    private val ctrl: IViewsCtrl
) : JPanel(BorderLayout()), Destroyable {
  private var ctrlRow: CartCtrlRowComponent? = null

  init {
    border = BorderFactory.createEmptyBorder(0, 0, 8, 0)
    refresh(cart)
  }

  fun refresh(nextCart: Cart) {
    cart = nextCart
    removeAll()
    ctrlRow?.onBeforeDestroy()
    ctrlRow = CartCtrlRowComponent(cart, ctrl)
    add(ctrlRow!!, BorderLayout.PAGE_START)

    if (nextCart.expanded) {
      add(getItemsPanel(), BorderLayout.PAGE_END)
    }
  }

  private fun getItemsPanel(): JPanel {
    val panel = JPanel(GridBagLayout())
    var row = 1

    panel.border = BorderFactory.createEmptyBorder(4, 0, 0, 0)

    if (cart.items.isNotEmpty()) {
      addItemRow(panel, 0, true, "item", "split", "actual", "goal")
    }

    cart.items.forEach {
      addItemRow(panel, row++, false, it.name, "${it.splitAmt}",
          "${it.actualAmt}", "${it.target}")
    }

    return panel
  }

  private fun addItemRow(
      itemsPanel: JPanel,
      row: Int,
      header: Boolean,
      name: String,
      splitAmt: String,
      actualAmt: String,
      targetAmt: String
  ) {
    val gbc = GridBagConstraints()
    gbc.fill = GridBagConstraints.HORIZONTAL
    gbc.weightx = 0.1
    gbc.gridy = row
    gbc.gridx = 0
    gbc.gridwidth = 3
    itemsPanel.add(ItemGridLabel(name, header = header), gbc)

    gbc.weightx = 0.3
    gbc.gridwidth = 2
    gbc.gridx += 3
    listOf(splitAmt, actualAmt, targetAmt).forEach {
      itemsPanel.add(ItemGridLabel(it, header = header, number = !header), gbc)
      gbc.gridx += 2
    }
  }

  override fun onBeforeDestroy() {
    ctrlRow?.onBeforeDestroy()
  }

  fun shouldRefresh(nextCart: Cart) = cart != nextCart

  // TODO tooltip describing what split/actual/goal are
  // make tooltip for each item label the whole rows info
  // only do tooltip over the items name instead of the numbers
  // make tooltip for cart name show the cart name + actual % done + split % done
  // can also make the cart name label have two 3px horizontal progress
  // bars below the name to indicate each progress
  // name when closed should look like (w/o progressbars) "> priest in peril"
  // refer to
  class ItemGridLabel(
      content: String,
      number: Boolean = false,
      header: Boolean = false
  ) : JLabel(content) {
    init {
      if (header) {
        foreground = Color(255, 255, 255, 128)
      }
      if (number) {
        font = Font(font.fontName, font.style, 14)
      }
      border = EmptyBorder(0, 0, 0, 0)
      toolTipText = content
    }
  }
}