package com.itemcarts.ui.cartsview

import com.itemcarts.ui.*
import net.runelite.client.ui.ColorScheme
import javax.swing.border.EmptyBorder

class CartNameButton(name: String, onClick: () -> Unit) : LabelButton(
  LabelButtonOpts(
    text = name,
    textColor = TEXT_PRIMARY,
    textHoverColor = ColorScheme.BRAND_ORANGE,
    bgColor = ColorScheme.DARKER_GRAY_COLOR,
    bgHoverColor = ColorScheme.DARKER_GRAY_HOVER_COLOR,
    onClick = onClick
  )
) {
  init {
    horizontalAlignment = LEFT
    border = EmptyBorder(0, 8, 0, 8)
  }
}

class StatusButton(completed: Boolean) : IconButton(
  LabelButtonOpts(
    text = "✓",
    tooltipText = if (completed) "complete" else "incomplete",
    textColor = if (completed) SUCCESS_PRIMARY else TEXT_DISABLED,
    textHoverColor = if (completed) SUCCESS_PRIMARY else TEXT_DISABLED,
    bgColor = ColorScheme.DARKER_GRAY_COLOR,
    bgHoverColor = ColorScheme.DARKER_GRAY_COLOR
  )
)

class DeleteCartButton(onClick: () -> Unit) : IconButton(
  LabelButtonOpts(
    text = "✘",
    textColor = TEXT_DISABLED,
    textHoverColor = DANGEROUS_PRIMARY,
    tooltipText = "Delete Cart (double click)",
    requireDoubleClick = true,
    onClick = onClick
  )
)

class EditCartButton(onClick: () -> Unit) : ViewChangeListener, IconButton(
  LabelButtonOpts(
    text = "✎",
    textColor = TEXT_DISABLED,
    tooltipText = "Edit Cart",
    onClick = onClick
  )
) {
  init {
    ViewManager.addViewChangeListener(this)
  }

  override fun onBeforeDestroy() {
    super.onBeforeDestroy()
    ViewManager.removeViewChangeListener(this)
  }

  override fun onViewChanged(latestView: View) {
    refreshColors(false)
  }
}