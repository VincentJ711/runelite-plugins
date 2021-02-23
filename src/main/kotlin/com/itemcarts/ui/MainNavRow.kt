package com.itemcarts.ui

import net.runelite.client.ui.ColorScheme
import java.awt.Component
import java.awt.Dimension
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JPanel
import javax.swing.border.EmptyBorder
import javax.swing.border.MatteBorder

class MainNavRow(
  private val viewManager: () -> ViewManager
) : JPanel(), Destroyable, ViewChangeListener {

  private val cartsBtn = LabelButton(
    LabelButtonOpts(
      text = "carts",
      bgColor = ColorScheme.DARKER_GRAY_COLOR,
      bgHoverColor = ColorScheme.DARKER_GRAY_HOVER_COLOR,
      textColor = TEXT_PRIMARY,
      onClick = { viewManager().goToCartsView() }
    )
  )

  private val summaryBtn = LabelButton(
    LabelButtonOpts(
      text = "summary",
      bgColor = ColorScheme.DARKER_GRAY_COLOR,
      bgHoverColor = ColorScheme.DARKER_GRAY_HOVER_COLOR,
      textColor = TEXT_PRIMARY,
      onClick = { viewManager().goToSummaryView() }
    )
  )

  private val selectedBorder = MatteBorder(0, 0, 1, 0, ColorScheme.BRAND_ORANGE)
  private val noBorder = EmptyBorder(0, 0, 1, 0)

  init {
    layout = BoxLayout(this, BoxLayout.X_AXIS)
    background = ColorScheme.DARK_GRAY_COLOR

    cartsBtn.alignmentX = Component.CENTER_ALIGNMENT
    summaryBtn.alignmentX = Component.CENTER_ALIGNMENT

    cartsBtn.minimumSize = Dimension(72, 32)
    cartsBtn.preferredSize = Dimension(72, 32)
    cartsBtn.maximumSize = Dimension(72, 32)
    cartsBtn.border = selectedBorder

    summaryBtn.minimumSize = Dimension(72, 32)
    summaryBtn.preferredSize = Dimension(72, 32)
    summaryBtn.maximumSize = Dimension(72, 32)
    summaryBtn.border = noBorder

    add(cartsBtn)
    add(Box.createHorizontalStrut(8))
    add(summaryBtn)
    ViewManager.addViewChangeListener(this)
  }

  override fun onBeforeDestroy() {
    cartsBtn.onBeforeDestroy()
    summaryBtn.onBeforeDestroy()
    ViewManager.removeViewChangeListener(this)
  }

  override fun onViewChanged(latestView: View) {
    if (latestView == View.CARTS) {
      cartsBtn.border = selectedBorder
      summaryBtn.border = noBorder
      cartsBtn.refreshColors(true)
      summaryBtn.refreshColors(false)
    } else if (latestView == View.SUMMARY) {
      summaryBtn.border = selectedBorder
      cartsBtn.border = noBorder
      summaryBtn.refreshColors(true)
      cartsBtn.refreshColors(false)
    }
  }
}