package com.itemcarts.ui.cartsview

import com.itemcarts.Cart
import com.itemcarts.CartItem
import com.itemcarts.ui.TEXT_SECONDARY
import net.runelite.client.util.QuantityFormatter
import java.awt.Dimension
import java.awt.Font
import java.awt.GridLayout
import javax.swing.BoxLayout
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

    val nameColumn = boxPanel()
    val currAmtColumn = boxPanel()
    val reqdAmtColumn = boxPanel()
    val numbersPanel = JPanel(GridLayout(1, 2, 2, 0))

    // ensures name doesnt exceed the width of the plugin panel
    nameColumn.maximumSize = Dimension(0, 0)
    nameColumn.preferredSize = Dimension(0, 0)

    val requiredLabel = JLabel("required")
    requiredLabel.toolTipText = "= reusable + consumable"

    nameColumn.add(JLabel("item name"))
    currAmtColumn.add(JLabel("current"))
    reqdAmtColumn.add(requiredLabel)

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

  private fun boxPanel(): JPanel {
    val panel = JPanel()
    panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)
    return panel
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
        if (!required) ""
        else " = ${fmt(item.reusableAmt)} + ${fmt(item.consumableAmt)}"

    return label
  }

  private fun fmt(n: Long): String = QuantityFormatter.formatNumber(n)
}