package com.itemcarts.haha.ui.cartsview

import com.itemcarts.haha.ui.shared.CharButton
import java.awt.Color

class DeleteCartButton(onClick: () -> Unit) :
  CharButton('✘', "Delete Cart (double click)", true, onClick) {
  init {
    foreground = Color.RED
  }
}