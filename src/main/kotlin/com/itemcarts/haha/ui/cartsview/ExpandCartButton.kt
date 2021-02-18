package com.itemcarts.haha.ui.cartsview

import com.itemcarts.haha.ui.shared.CharButton
import net.runelite.client.ui.ColorScheme

class ExpandCartButton(
  initiallyExpanded: Boolean,
  private val onClick: () -> Unit
) : CharButton(
  char = if (initiallyExpanded) '▲' else '▼',
  bgColor = ColorScheme.DARKER_GRAY_COLOR,
  bgHoverColor = ColorScheme.DARKER_GRAY_HOVER_COLOR
) {
  var expanded = initiallyExpanded
    private set

  init {
    callback = {
      expanded = !expanded
      text = if (this.expanded) "▲" else "▼"
      onClick()
    }
  }
}