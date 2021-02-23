package com.itemcarts.ui.summaryview

import com.itemcarts.ui.TEXT_PRIMARY
import net.runelite.client.ui.ColorScheme
import net.runelite.client.ui.FontManager
import java.awt.BorderLayout
import java.awt.Color
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

class SummaryItemHeaderPanel : JPanel(BorderLayout()) {
  init {
    val rightPanel = JPanel()
    val name = JLabel("item name")
    val current = JLabel("curr")
    val required = JLabel("reqd")
    val spacer = Box.createHorizontalStrut(28)

    name.foreground = TEXT_PRIMARY
    name.font = FontManager.getRunescapeFont()

    current.foreground = TEXT_PRIMARY
    current.font = FontManager.getRunescapeFont()
    current.preferredSize = SummaryItemPanel.numLabelSize
    current.maximumSize = SummaryItemPanel.numLabelSize
    current.toolTipText = "how much of this item you currently have"

    required.foreground = TEXT_PRIMARY
    required.font = FontManager.getRunescapeFont()
    required.preferredSize = SummaryItemPanel.numLabelSize
    required.maximumSize = SummaryItemPanel.numLabelSize
    required.toolTipText = "how much of this item you need"

    rightPanel.layout = BoxLayout(rightPanel, BoxLayout.X_AXIS)
    rightPanel.border = EmptyBorder(0, 4, 0, 0)
    rightPanel.add(current)
    rightPanel.add(required)
    rightPanel.add(spacer)

    add(name, BorderLayout.CENTER)
    add(rightPanel, BorderLayout.EAST)
    background = ColorScheme.DARK_GRAY_COLOR
    rightPanel.background = ColorScheme.DARK_GRAY_COLOR
  }
}