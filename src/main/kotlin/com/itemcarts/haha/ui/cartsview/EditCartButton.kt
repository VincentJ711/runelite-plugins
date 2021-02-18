package com.itemcarts.haha.ui.cartsview

import com.itemcarts.haha.ui.shared.CharButton

class EditCartButton(onClick: () -> Unit) : CharButton(
  char = '✎',
  tooltipText = "Edit Cart",
  callback = onClick
)