package com.itemcarts.ui.summaryview

import java.awt.BorderLayout
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

class SummaryItemHeaderPanel : JPanel(BorderLayout()) {
  init {
    val rightPanel = JPanel()
    val name = JLabel("item name")
    val current = JLabel("current")
    val required = JLabel("required")
    val spacer = Box.createRigidArea(SummaryItemPanel.statusBtnSize)

    current.preferredSize = SummaryItemPanel.numLabelSize
    current.maximumSize = SummaryItemPanel.numLabelSize
    current.toolTipText = "how much of this item you currently have"

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
  }
}