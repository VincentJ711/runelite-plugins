package com.itemcarts.haha.ui.cartsview

import com.itemcarts.haha.ui.*
import net.runelite.client.ui.ColorScheme
import javax.swing.border.EmptyBorder

class CartNameButton(name: String, onClick: () -> Unit) : LabelButton(
  LabelButtonOpts(
    text = name,
    textColor = TEXT_SECONDARY,
    textHoverColor = TEXT_PRIMARY,
    bgColor = ColorScheme.DARKER_GRAY_COLOR,
    bgHoverColor = ColorScheme.DARKER_GRAY_HOVER_COLOR,
    callback = onClick
  )
) {
  init {
    horizontalAlignment = LEFT
    border = EmptyBorder(0, 8, 0, 8)
  }
}

class StatusButton(completed: Boolean) : IconButton(
  LabelButtonOpts(
    text = if (completed) "✓" else "✘",
    tooltipText = if (completed) "complete" else "incomplete",
    textColor = if (completed) SUCCESS_PRIMARY else DANGEROUS_PRIMARY,
    textHoverColor = if (completed) SUCCESS_PRIMARY else DANGEROUS_PRIMARY,
    bgColor = ColorScheme.DARKER_GRAY_COLOR,
    bgHoverColor = ColorScheme.DARKER_GRAY_COLOR
  )
)

class DeleteCartButton(onClick: () -> Unit) : IconButton(
  LabelButtonOpts(
    text = "✘",
    textColor = DANGEROUS_SECONDARY,
    textHoverColor = DANGEROUS_PRIMARY,
    tooltipText = "Delete Cart (double click)",
    requireDoubleClick = true,
    callback = onClick
  )
)

class EditCartButton(onClick: () -> Unit) : IconButton(
  LabelButtonOpts(
    text = "✎",
    tooltipText = "Edit Cart",
    callback = onClick
  )
)