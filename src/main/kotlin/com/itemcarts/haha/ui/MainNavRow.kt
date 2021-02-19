package com.itemcarts.haha.ui

import javax.inject.Inject
import javax.inject.Singleton
import javax.swing.BoxLayout
import javax.swing.JPanel

@Singleton
class MainNavRow : JPanel(), Destroyable {
  @Inject
  private lateinit var uiManager: UiManager
  private val cartsBtn = LabelButton(
    LabelButtonOpts(
      text = "carts",
      onClick = { uiManager.goToCartsView() }
    )
  )

  private val summaryBtn = LabelButton(
    LabelButtonOpts(
      text = "summary",
      onClick = { uiManager.goToSummaryView() }
    )
  )

  init {
    layout = BoxLayout(this, BoxLayout.X_AXIS)
    add(JPanel())
    add(cartsBtn)
    add(summaryBtn)
    add(JPanel())
  }

  override fun onBeforeDestroy() {
    cartsBtn.onBeforeDestroy()
    summaryBtn.onBeforeDestroy()
  }
}