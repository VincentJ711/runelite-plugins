package com.itemcarts.ui.summaryview

import com.itemcarts.ontoEDT
import com.itemcarts.ui.UiManager
import javax.swing.JPanel

interface ISummaryViewManager {
  /** updates the summary view to reflect the given summary */
  fun updateSummary(summary: Map<String, Long>)
}

class SummaryViewManager : ISummaryViewManager {
  lateinit var uiManager: UiManager
  val rootPanel = JPanel()

  override fun updateSummary(summary: Map<String, Long>) = ontoEDT {
    // TODO
  }
}