package com.itemcarts.ui

import net.runelite.client.ui.ColorScheme
import java.awt.Component
import java.awt.Dimension
import javax.inject.Inject
import javax.inject.Provider
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JPanel

class MainNavRow : JPanel(), Destroyable {
  @Inject
  private lateinit var viewManager: Provider<ViewManager>
  private val cartsBtn = LabelButton(
    LabelButtonOpts(
      text = "carts",
      bgColor = ColorScheme.DARKER_GRAY_COLOR,
      bgHoverColor = ColorScheme.DARKER_GRAY_HOVER_COLOR,
      textColor = TEXT_PRIMARY,
      onClick = { viewManager.get().goToCartsView() }
    )
  )

  private val summaryBtn = LabelButton(
    LabelButtonOpts(
      text = "summary",
      bgColor = ColorScheme.DARKER_GRAY_COLOR,
      bgHoverColor = ColorScheme.DARKER_GRAY_HOVER_COLOR,
      textColor = TEXT_PRIMARY,
      onClick = { viewManager.get().goToSummaryView() }
    )
  )

  init {
    layout = BoxLayout(this, BoxLayout.X_AXIS)
    background = ColorScheme.DARK_GRAY_COLOR

    cartsBtn.alignmentX = Component.CENTER_ALIGNMENT
    summaryBtn.alignmentX = Component.CENTER_ALIGNMENT

    cartsBtn.minimumSize = Dimension(72, 32)
    cartsBtn.preferredSize = Dimension(72, 32)
    cartsBtn.maximumSize = Dimension(72, 32)

    summaryBtn.minimumSize = Dimension(72, 32)
    summaryBtn.preferredSize = Dimension(72, 32)
    summaryBtn.maximumSize = Dimension(72, 32)

    add(cartsBtn)
    add(Box.createHorizontalStrut(8))
    add(summaryBtn)
  }

  override fun onBeforeDestroy() {
    cartsBtn.onBeforeDestroy()
    summaryBtn.onBeforeDestroy()
  }
}