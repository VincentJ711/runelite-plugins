package com.itemcarts.haha.ui.cartsview

import com.itemcarts.haha.ui.shared.DANGEROUS_PRIMARY
import com.itemcarts.haha.ui.shared.IconButton
import com.itemcarts.haha.ui.shared.LabelButtonOpts
import com.itemcarts.haha.ui.shared.SUCCESS_PRIMARY
import net.runelite.client.ui.ColorScheme
import java.awt.Color

class StatusButton(completed: Boolean) : IconButton(
  LabelButtonOpts(
    text = if (completed) "✓" else "✘",
    tooltipText = if (completed) "complete" else "incomplete",
    textColor = if (completed) SUCCESS_PRIMARY else DANGEROUS_PRIMARY,
    textHoverColor = if (completed) SUCCESS_PRIMARY else DANGEROUS_PRIMARY,
    bgColor = ColorScheme.DARKER_GRAY_COLOR,
    bgHoverColor = ColorScheme.DARKER_GRAY_COLOR
  )
)