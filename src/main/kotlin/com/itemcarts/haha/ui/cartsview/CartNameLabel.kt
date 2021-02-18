package com.itemcarts.haha.ui.cartsview

import com.itemcarts.haha.ui.shared.LabelButton
import com.itemcarts.haha.ui.shared.LabelButtonOpts
import com.itemcarts.haha.ui.shared.TEXT_PRIMARY
import com.itemcarts.haha.ui.shared.TEXT_SECONDARY
import net.runelite.client.ui.ColorScheme
import javax.swing.SwingConstants
import javax.swing.border.EmptyBorder

class CartNameLabel(name: String, onClick: () -> Unit) : LabelButton(
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
    horizontalAlignment = SwingConstants.LEFT
    border = EmptyBorder(0, 8, 0, 8)
  }
}