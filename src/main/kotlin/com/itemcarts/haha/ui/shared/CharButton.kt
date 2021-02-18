package com.itemcarts.haha.ui.shared

import com.itemcarts.haha.ui.Destroyable
import net.runelite.client.ui.ColorScheme
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JLabel
import javax.swing.SwingConstants

/** simple icon button */
open class CharButton(
  char: Char,
  tooltipText: String? = null,
  private val bgColor: Color = ColorScheme.DARK_GRAY_COLOR,
  private val bgHoverColor: Color = ColorScheme.DARK_GRAY_HOVER_COLOR,
  private val textColor: Color = ColorScheme.BRAND_ORANGE_TRANSPARENT,
  private val textHoverColor: Color = ColorScheme.BRAND_ORANGE,
  private val requireDoubleClick: Boolean = false,
  protected var callback: (() -> Unit)? = null
) : JLabel("$char", SwingConstants.CENTER), Destroyable {
  private val adapter = Adapter()

  init {
    background = bgColor
    foreground = textColor
    isOpaque = true
    preferredSize = Dimension(36, 36)
    font = Font("Arial", Font.BOLD, 14)
    toolTipText = tooltipText
    addMouseListener(adapter)
  }

  override fun onBeforeDestroy() = removeMouseListener(adapter)

  private fun refresh(hovering: Boolean) {
    foreground = if (hovering) textHoverColor else textColor
    background =
      if (hovering) bgHoverColor else bgColor
  }

  private inner class Adapter : MouseAdapter() {
    private var lastClickTime = System.currentTimeMillis()

    override fun mouseEntered(e: MouseEvent?) = refresh(true)
    override fun mouseExited(e: MouseEvent?) = refresh(false)
    override fun mousePressed(e: MouseEvent?) {
      val now = System.currentTimeMillis()
      if (!requireDoubleClick || now - lastClickTime < 350) {
        callback?.invoke()
      }
      lastClickTime = now
    }
  }
}

