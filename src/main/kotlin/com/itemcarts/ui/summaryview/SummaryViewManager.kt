package com.itemcarts.ui.summaryview

import com.itemcarts.ontoEDT
import com.itemcarts.ui.MainNavRow
import com.itemcarts.ui.ViewManager
import net.runelite.client.ui.ColorScheme
import java.awt.BorderLayout
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import javax.swing.BoxLayout
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

interface ISummaryViewManager {
  /** updates the summary view to reflect the given summary */
  fun updateSummary(summary: Map<String, Long>)
}

@Singleton
class SummaryViewManager @Inject constructor(
  private val viewManager: Provider<ViewManager>
) : ISummaryViewManager {
  val rootPanel = JPanel(BorderLayout())

  init {
    val ctrlPanel = JPanel()
    ctrlPanel.layout = BoxLayout(ctrlPanel, BoxLayout.Y_AXIS)
    ctrlPanel.background = ColorScheme.DARK_GRAY_COLOR
    ctrlPanel.border = EmptyBorder(8, 0, 8, 0)
    ctrlPanel.add(MainNavRow { viewManager.get() })
    rootPanel.add(ctrlPanel, BorderLayout.NORTH)
  }

  override fun updateSummary(summary: Map<String, Long>) = ontoEDT {
    // TODO
  }
}