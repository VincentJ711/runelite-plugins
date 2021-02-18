package com.itemcarts.haha.ui.cartsview

import com.itemcarts.haha.ui.shared.CharButton
import net.runelite.client.ui.ColorScheme

class EditCartButton(onClick: () -> Unit) :
  CharButton('✎', "Edit Cart", false, onClick) {
  init {
    foreground = ColorScheme.BRAND_ORANGE
  }
}