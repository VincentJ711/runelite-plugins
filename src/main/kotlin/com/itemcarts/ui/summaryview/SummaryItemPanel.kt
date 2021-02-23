package com.itemcarts.ui.summaryview

import com.itemcarts.SummaryItem
import com.itemcarts.ui.*
import com.itemcarts.ui.cartsview.CartItemsPanel
import net.runelite.client.ui.ColorScheme
import net.runelite.client.util.QuantityFormatter.quantityToStackSize as fmtShort
import net.runelite.client.util.QuantityFormatter.formatNumber as fmtLong
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.BoxLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

class SummaryItemPanel(item: SummaryItem) : JPanel(BorderLayout()) {
  companion object {
    val numLabelSize = Dimension(48, 20)
    val statusBtnSize = Dimension(20, 20)
  }

  private val statusBtn = IconButton(
    LabelButtonOpts(
      text = "✓",
      tooltipText = if (item.completed) {
        "complete"
      } else {
        "${fmtLong(item.requiredAmt - item.currentAmt)} left"
      },
      textColor = if (item.completed) SUCCESS_PRIMARY else TEXT_DISABLED,
      textHoverColor = if (item.completed) SUCCESS_PRIMARY else TEXT_DISABLED,
      bgColor = ColorScheme.DARK_GRAY_COLOR,
      bgHoverColor = ColorScheme.DARK_GRAY_COLOR
    )
  )

  init {
    val rightPanel = JPanel()
    val name = JLabel(item.name)
    val current = JLabel(fmtShort(item.currentAmt))
    val required = JLabel(fmtShort(item.requiredAmt))

    name.preferredSize = Dimension(0, 0)
    name.font = CartItemsPanel.itemNameLabelFont

    if (item.name.length > 14) {
      name.toolTipText = item.name
    }

    current.preferredSize = numLabelSize
    current.maximumSize = numLabelSize
    current.toolTipText = fmtLong(item.currentAmt)
    current.font = CartItemsPanel.numberLabelFont

    required.preferredSize = numLabelSize
    required.maximumSize = numLabelSize
    required.toolTipText = fmtLong(item.requiredAmt)
    required.font = CartItemsPanel.numberLabelFont

    statusBtn.preferredSize = statusBtnSize
    statusBtn.maximumSize = statusBtnSize

    rightPanel.layout = BoxLayout(rightPanel, BoxLayout.X_AXIS)
    rightPanel.border = EmptyBorder(0, 4, 0, 0)
    rightPanel.add(current)
    rightPanel.add(required)
    rightPanel.add(statusBtn)

    add(name, BorderLayout.CENTER)
    add(rightPanel, BorderLayout.EAST)
  }
}