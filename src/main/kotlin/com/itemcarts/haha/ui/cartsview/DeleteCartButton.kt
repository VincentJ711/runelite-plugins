package com.itemcarts.haha.ui.cartsview

import com.itemcarts.haha.ui.shared.CharButton
import java.awt.Color

class DeleteCartButton(onClick: () -> Unit) : CharButton(
  char = '✘',
  textColor = Color(1f, 0f, 0f, 0.52f),
  textHoverColor = Color(255, 0, 0),
  tooltipText = "Delete Cart (double click)",
  requireDoubleClick = true,
  callback = onClick
)