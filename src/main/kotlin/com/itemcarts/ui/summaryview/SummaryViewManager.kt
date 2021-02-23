package com.itemcarts.ui.summaryview

import com.itemcarts.SummaryItem
import com.itemcarts.ontoEDT
import com.itemcarts.ui.MainNavRow
import com.itemcarts.ui.ViewManager
import net.runelite.client.ui.ColorScheme
import net.runelite.client.ui.components.CustomScrollBarUI
import java.awt.BorderLayout
import java.awt.Dimension
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import javax.swing.BoxLayout
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.border.EmptyBorder

interface ISummaryViewManager {
  /** updates the summary view to reflect the given summary */
  fun updateSummary(summary: Collection<SummaryItem>)
}

@Singleton
class SummaryViewManager @Inject constructor(
  private val viewManager: Provider<ViewManager>
) : ISummaryViewManager {
  val rootPanel = JPanel(BorderLayout())
  private val itemsListPanel = JPanel()

  init {
    val header = SummaryItemHeaderPanel()
    header.border = EmptyBorder(8, 8, 0, 8)

    val ctrlPanel = JPanel()
    ctrlPanel.layout = BoxLayout(ctrlPanel, BoxLayout.Y_AXIS)
    ctrlPanel.background = ColorScheme.DARK_GRAY_COLOR
    ctrlPanel.border = EmptyBorder(8, 0, 8, 0)
    ctrlPanel.add(MainNavRow { viewManager.get() })
    ctrlPanel.add(header)

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
    summary.forEach { item -> itemsListPanel.add(SummaryItemPanel(item)) }
  }
}