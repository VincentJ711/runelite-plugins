package com.itemcarts.haha.ui.cartsview

import com.itemcarts.haha.Cart
import com.itemcarts.haha.CartItem
import com.itemcarts.haha.ui.TEXT_SECONDARY
import net.runelite.client.util.QuantityFormatter
import java.awt.Font
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

class CartItemsPanel(cart: Cart) : JPanel(GridBagLayout()) {
  companion object {
    private val numberLabelFont: Font

    init {
      val x = JLabel()
      numberLabelFont = Font(x.font.name, x.font.style, 14)
    }
  }

  init {
    border = EmptyBorder(0, 4, 0, 4)
    addRow(0, JLabel("item name"), JLabel("current"), JLabel("required"))
    for ((index, item) in cart.items.withIndex()) {
      addRow(
        index + 1,
        itemNameLabel(item.name),
        numberLabel(item, false),
        numberLabel(item, true)
      )
    }
  }

  private fun itemNameLabel(name: String): JLabel {
    val label = JLabel(name)
    label.foreground = TEXT_SECONDARY
    return label
  }

  private fun numberLabel(item: CartItem, required: Boolean): JLabel {
    val chosen = if (required) item.requiredAmt else item.currentAmt
    val label = JLabel(QuantityFormatter.quantityToStackSize(chosen))

    label.foreground = TEXT_SECONDARY
    label.font = numberLabelFont
    label.toolTipText = fmt(chosen) +
        if (!required || item.reusableAmt == 0L || item.consumableAmt == 0L) ""
        else " = ${fmt(item.reusableAmt)} + ${fmt(item.consumableAmt)}"

    return label
  }

  private fun fmt(n: Long): String = QuantityFormatter.formatNumber(n)

  private fun addRow(row: Int, a: JLabel, b: JLabel, c: JLabel) {
    val gbc = GridBagConstraints()
    gbc.insets = Insets(0, 0, 0, 4)

    gbc.fill = GridBagConstraints.HORIZONTAL
    gbc.weightx = 0.5
    gbc.gridy = row
    gbc.gridx = 0
    gbc.gridwidth = 2
    add(a, gbc)

    gbc.weightx = 0.25
    gbc.gridwidth = 1
    gbc.gridx += 2
    add(b, gbc)
    gbc.gridx += 1
    add(c, gbc)
  }
}