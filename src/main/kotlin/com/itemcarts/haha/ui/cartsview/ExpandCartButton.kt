package com.itemcarts.haha.ui.cartsview

import com.itemcarts.haha.ui.shared.IconButton
import com.itemcarts.haha.ui.shared.LabelButton
import com.itemcarts.haha.ui.shared.LabelButtonOpts
import net.runelite.client.ui.ColorScheme

class ExpandCartButton(
  initiallyExpanded: Boolean,
  private val onClick: () -> Unit
) : IconButton(
  LabelButtonOpts(
    text = if (initiallyExpanded) "▲" else "▼",
    bgColor = ColorScheme.DARKER_GRAY_COLOR,
    bgHoverColor = ColorScheme.DARKER_GRAY_HOVER_COLOR
  )
) {
  var expanded = initiallyExpanded
    private set

  init {
    opts.callback = {
      expanded = !expanded
      text = if (expanded) "▲" else "▼"
      onClick()
    }
  }
}