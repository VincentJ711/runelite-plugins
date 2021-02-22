package com.itemcarts.ui

import java.awt.Dimension
import java.awt.Font

open class IconButton(opts: LabelButtonOpts) : LabelButton(opts) {
  init {
    preferredSize = Dimension(36, 36)
    // for unicode characters
    font = Font("Arial", Font.BOLD, 14)
  }
}