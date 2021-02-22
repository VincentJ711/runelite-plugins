package com.itemcarts.ui

import net.runelite.client.ui.ColorScheme
import java.awt.Component
import java.awt.Dimension
import javax.inject.Inject
import javax.inject.Provider
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JPanel
import javax.swing.border.EmptyBorder
import javax.swing.border.MatteBorder

class MainNavRow : JPanel(), Destroyable {
  @Inject
  private lateinit var viewManager: Provider<ViewManager>

  private val cartsBtn = LabelButton(
    LabelButtonOpts(
      text = "carts",
      bgColor = ColorScheme.DARKER_GRAY_COLOR,
      bgHoverColor = ColorScheme.DARKER_GRAY_HOVER_COLOR,
      textColor = TEXT_PRIMARY,
      onClick = { toCarts() }
    )
  )

  private val summaryBtn = LabelButton(
    LabelButtonOpts(
      text = "summary",
      bgColor = ColorScheme.DARKER_GRAY_COLOR,
      bgHoverColor = ColorScheme.DARKER_GRAY_HOVER_COLOR,
      textColor = TEXT_PRIMARY,
      onClick = { toSummary() }
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

    add(cartsBtn)
    add(Box.createHorizontalStrut(8))
    add(summaryBtn)
  }

  private fun toCarts() {
    cartsBtn.border = selectedBorder
    summaryBtn.border = noBorder
    viewManager.get().goToCartsView()
  }

  private fun toSummary() {
    cartsBtn.border = noBorder
    summaryBtn.border = selectedBorder
    viewManager.get().goToSummaryView()
  }

  override fun onBeforeDestroy() {
    cartsBtn.onBeforeDestroy()
    summaryBtn.onBeforeDestroy()
  }
}