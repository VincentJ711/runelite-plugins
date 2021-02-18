package com.itemcarts.haha.ui.cartsview

import com.itemcarts.haha.ui.shared.CharButton
import net.runelite.client.ui.ColorScheme
import java.awt.event.ActionListener

class ExpandCartButton(
  initiallyExpanded: Boolean,
  private val onClick: () -> Unit
) : CharButton(if (initiallyExpanded) '▲' else '▼', null, false, null) {
  var expanded = initiallyExpanded
    private set

  private val listener = ActionListener {
    this.expanded = !this.expanded
    text = if (this.expanded) "▲" else "▼"
    onClick()
  }

  init {
    background = ColorScheme.DARK_GRAY_COLOR
    foreground = ColorScheme.BRAND_ORANGE
    addActionListener(listener)
  }

  override fun onBeforeDestroy() {
    super.onBeforeDestroy()
    removeActionListener(listener)
  }
}