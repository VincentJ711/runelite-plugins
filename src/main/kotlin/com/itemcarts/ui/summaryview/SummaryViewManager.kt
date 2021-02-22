package com.itemcarts.ui.summaryview

import com.itemcarts.ontoEDT
import com.itemcarts.ui.MainNavRow
import com.itemcarts.ui.ViewManager
import java.awt.BorderLayout
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import javax.swing.JLabel
import javax.swing.JPanel

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
    rootPanel.add(MainNavRow())
    rootPanel.add(JLabel("summary view"))
  }

  override fun updateSummary(summary: Map<String, Long>) = ontoEDT {
    // TODO
  }
}