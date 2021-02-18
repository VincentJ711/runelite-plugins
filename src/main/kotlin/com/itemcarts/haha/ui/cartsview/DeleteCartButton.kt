package com.itemcarts.haha.ui.cartsview

import com.itemcarts.haha.ui.shared.DANGEROUS_SECONDARY
import com.itemcarts.haha.ui.shared.DANGEROUS_PRIMARY
import com.itemcarts.haha.ui.shared.IconButton
import com.itemcarts.haha.ui.shared.LabelButtonOpts

class DeleteCartButton(onClick: () -> Unit) : IconButton(
  LabelButtonOpts(
    text = "✘",
    textColor = DANGEROUS_SECONDARY,
    textHoverColor = DANGEROUS_PRIMARY,
    tooltipText = "Delete Cart (double click)",
    requireDoubleClick = true,
    callback = onClick
  )
)