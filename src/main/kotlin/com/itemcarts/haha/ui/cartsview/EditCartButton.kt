package com.itemcarts.haha.ui.cartsview

import com.itemcarts.haha.ui.shared.IconButton
import com.itemcarts.haha.ui.shared.LabelButtonOpts

class EditCartButton(onClick: () -> Unit) : IconButton(
  LabelButtonOpts(
    text = "✎",
    tooltipText = "Edit Cart",
    callback = onClick
  )
)