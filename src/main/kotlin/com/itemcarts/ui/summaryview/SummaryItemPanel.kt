package com.itemcarts.ui.summaryview

import com.itemcarts.SummaryItem
import com.itemcarts.ui.*
import net.runelite.client.ui.ColorScheme
import net.runelite.client.ui.FontManager
import net.runelite.client.util.QuantityFormatter.formatNumber as fmtLong
import net.runelite.client.util.QuantityFormatter.quantityToStackSize as fmtShort
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Font
import javax.swing.BoxLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

class SummaryItemPanel(item: SummaryItem) : JPanel(BorderLayout()) {
  companion object {
    val numLabelSize = Dimension(36, 20)
    val statusBtnSize = Dimension(20, 20)
    private val labelFont: Font

    init {
      val rsFont = FontManager.getRunescapeFont()
      labelFont = Font(rsFont.name, rsFont.style, 16)
    }
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

    name.foreground = TEXT_SECONDARY
    name.preferredSize = Dimension(0, 0)
    name.font = labelFont

    if (item.name.length > 14) {
      name.toolTipText = item.name
    }

    current.foreground = TEXT_SECONDARY
    current.preferredSize = numLabelSize
    current.maximumSize = numLabelSize
    current.toolTipText = fmtLong(item.currentAmt)
    current.font = labelFont

    required.foreground = TEXT_SECONDARY
    required.preferredSize = numLabelSize
    required.maximumSize = numLabelSize
    required.toolTipText = fmtLong(item.requiredAmt)
    required.font = labelFont

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