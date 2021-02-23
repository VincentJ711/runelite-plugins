package com.itemcarts.ui.summaryview

import com.itemcarts.SummaryItem
import com.itemcarts.ontoEDT
import com.itemcarts.ui.*
import com.itemcarts.ui.cartsview.CartItemsPanel
import net.runelite.client.ui.ColorScheme
import net.runelite.client.ui.components.CustomScrollBarUI
import net.runelite.client.util.QuantityFormatter
import java.awt.BorderLayout
import java.awt.Dimension
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import javax.swing.*
import javax.swing.border.EmptyBorder

interface ISummaryViewManager {
  /** updates the summary view to reflect the given summary */
  fun updateSummary(summary: Collection<SummaryItem>)
}

@Singleton
class SummaryViewManager @Inject constructor(
  private val viewManager: Provider<ViewManager>
) : ISummaryViewManager {
  companion object {
    private const val numberColWidth = 36
  }

  val rootPanel = JPanel(BorderLayout())
  private val itemsListPanel = JPanel()

  init {
    val ctrlPanel = JPanel()
    ctrlPanel.layout = BoxLayout(ctrlPanel, BoxLayout.Y_AXIS)
    ctrlPanel.background = ColorScheme.DARK_GRAY_COLOR
    ctrlPanel.border = EmptyBorder(8, 0, 8, 0)
    ctrlPanel.add(MainNavRow { viewManager.get() })

    val itemsListPanelWrapper = JPanel(BorderLayout())
    val filler = JPanel()
    val scrollPane = JScrollPane(itemsListPanelWrapper)

    filler.background = ColorScheme.DARK_GRAY_COLOR
    scrollPane.verticalScrollBar.preferredSize = Dimension(8, 0)
    scrollPane.verticalScrollBar.ui = CustomScrollBarUI()
    scrollPane.verticalScrollBar.unitIncrement = 16
    scrollPane.border = EmptyBorder(0, 0, 0, 0)

    itemsListPanel.background = ColorScheme.DARK_GRAY_COLOR
    itemsListPanel.border = EmptyBorder(0, 8, 0, 8)
    itemsListPanel.layout = BoxLayout(itemsListPanel, BoxLayout.Y_AXIS)

    itemsListPanelWrapper.add(itemsListPanel, BorderLayout.NORTH)
    itemsListPanelWrapper.add(filler, BorderLayout.CENTER)
    rootPanel.add(ctrlPanel, BorderLayout.NORTH)
    rootPanel.add(scrollPane, BorderLayout.CENTER)
  }

  override fun updateSummary(summary: Collection<SummaryItem>) = ontoEDT {
    itemsListPanel.removeAll()
    summary.forEach { item -> itemsListPanel.add(renderSummaryItem(item)) }
  }

  private fun renderSummaryItem(item: SummaryItem): JComponent {
    val panel = JPanel(BorderLayout())
    val name = JLabel(item.name)
    val rightPanel = JPanel()
    val status = statusBtn(item)
    val current =
      JLabel(QuantityFormatter.quantityToStackSize(item.currentAmt))
    val required =
      JLabel(QuantityFormatter.quantityToStackSize(item.requiredAmt))

    name.preferredSize = Dimension(0, 0)

    if (item.name.length > 14) {
      name.toolTipText = item.name
    }

    current.preferredSize = Dimension(numberColWidth, 24)
    current.maximumSize = Dimension(numberColWidth, 24)
    current.toolTipText = fmtLong(item.currentAmt)
    current.font = CartItemsPanel.numberLabelFont

    required.preferredSize = Dimension(numberColWidth, 24)
    required.maximumSize = Dimension(numberColWidth, 24)
    required.toolTipText = fmtLong(item.requiredAmt)
    required.font = CartItemsPanel.numberLabelFont

    rightPanel.layout = BoxLayout(rightPanel, BoxLayout.X_AXIS)
    rightPanel.border = EmptyBorder(0, 4, 0, 0)
    rightPanel.add(current)
    rightPanel.add(required)
    rightPanel.add(status)

    panel.add(name, BorderLayout.CENTER)
    panel.add(rightPanel, BorderLayout.EAST)
    return panel
  }

  private fun statusBtn(item: SummaryItem): IconButton {
    val left = item.requiredAmt - item.currentAmt
    val btn = IconButton(
      LabelButtonOpts(
        text = "✓",
        tooltipText = if (left <= 0) "complete" else "${fmtLong(left)} left",
        textColor = if (left <= 0) SUCCESS_PRIMARY else TEXT_DISABLED,
        textHoverColor = if (left <= 0) SUCCESS_PRIMARY else TEXT_DISABLED,
        bgColor = ColorScheme.DARK_GRAY_COLOR,
        bgHoverColor = ColorScheme.DARK_GRAY_COLOR
      )
    )
    btn.preferredSize = Dimension(24, 24)
    btn.maximumSize = Dimension(24, 24)
    return btn
  }

  private fun fmtLong(n: Long): String = QuantityFormatter.formatNumber(n)
}