package com.itemcarts.haha.ui

import net.runelite.client.ui.ColorScheme
import java.awt.Color
import java.awt.Dimension
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JLabel
import javax.swing.SwingConstants

data class LabelButtonOpts(
  val text: String,
  val tooltipText: String? = null,
  val bgColor: Color = ColorScheme.DARK_GRAY_COLOR,
  val bgHoverColor: Color = ColorScheme.DARK_GRAY_HOVER_COLOR,
  val textColor: Color = ColorScheme.BRAND_ORANGE_TRANSPARENT,
  val textHoverColor: Color = ColorScheme.BRAND_ORANGE,
  val requireDoubleClick: Boolean = false,
  var onClick: (() -> Unit)? = null
)

open class LabelButton(
  val opts: LabelButtonOpts
) : JLabel(opts.text, SwingConstants.CENTER), Destroyable {
  private val adapter = Adapter()

  init {
    preferredSize = Dimension(0, 36)
    background = opts.bgColor
    foreground = opts.textColor
    isOpaque = true
    toolTipText = opts.tooltipText
    addMouseListener(adapter)
  }

  override fun onBeforeDestroy() = removeMouseListener(adapter)

  private fun onHoverChanged(hovering: Boolean) {
    foreground = if (hovering) opts.textHoverColor else opts.textColor
    background = if (hovering) opts.bgHoverColor else opts.bgColor
  }

  private inner class Adapter : MouseAdapter() {
    private var lastClickTime = System.currentTimeMillis()

    override fun mouseEntered(e: MouseEvent?) = onHoverChanged(true)
    override fun mouseExited(e: MouseEvent?) = onHoverChanged(false)
    override fun mousePressed(e: MouseEvent?) {
      val now = System.currentTimeMillis()
      if (!opts.requireDoubleClick || now - lastClickTime < 350) {
        opts.onClick?.invoke()
      }
      lastClickTime = now
    }
  }
}