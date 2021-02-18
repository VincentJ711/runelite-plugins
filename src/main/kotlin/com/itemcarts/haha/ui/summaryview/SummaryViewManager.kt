package com.itemcarts.haha.ui.summaryview

import com.itemcarts.haha.ontoEDT
import com.itemcarts.haha.ui.UiManager
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