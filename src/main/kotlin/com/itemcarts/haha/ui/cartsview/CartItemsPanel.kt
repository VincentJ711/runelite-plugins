package com.itemcarts.haha.ui.cartsview

import com.itemcarts.haha.Cart
import com.itemcarts.haha.CartItem
import com.itemcarts.haha.ui.TEXT_SECONDARY
import net.runelite.client.util.QuantityFormatter
import java.awt.Font
import java.awt.GridLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

class CartItemsPanel(cart: Cart) : JPanel(GridLayout(1, 2, 2, 0)) {
  companion object {
    private val numberLabelFont: Font

    init {
      val x = JLabel()
      numberLabelFont = Font(x.font.name, x.font.style, 14)
    }
  }

  init {
    border = EmptyBorder(0, 4, 0, 4)

    val nameColumn = JPanel(GridLayout(cart.items.size + 1, 1))
    val currAmtColumn = JPanel(GridLayout(cart.items.size + 1, 1))
    val reqdAmtColumn = JPanel(GridLayout(cart.items.size + 1, 1))
    val numbersPanel = JPanel(GridLayout(1, 2, 2, 0))

    nameColumn.add(JLabel("item name"))
    currAmtColumn.add(JLabel("current"))
    reqdAmtColumn.add(JLabel("required"))

    cart.items.forEach { i ->
      nameColumn.add(itemNameLabel(i.name))
      currAmtColumn.add(numberLabel(i, false))
      reqdAmtColumn.add(numberLabel(i, true))
    }

    numbersPanel.add(currAmtColumn)
    numbersPanel.add(reqdAmtColumn)
    add(nameColumn)
    add(numbersPanel)
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
}