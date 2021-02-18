package com.itemcarts.haha.ui.shared

import com.itemcarts.haha.ui.Destroyable
import java.awt.Dimension
import java.awt.Font
import java.awt.Insets
import java.awt.event.ActionListener
import javax.swing.JButton

/** simple icon button */
open class CharButton(
  char: Char,
  tooltipText: String?,
  requireDoubleClick: Boolean,
  private val callback: (() -> Unit)?
) : JButton("$char"), Destroyable {
  private var lastClickTime = System.currentTimeMillis()
  private var handler: ActionListener? = null

  init {
    isBorderPainted = false
    isContentAreaFilled = false
    isFocusPainted = false
    margin = Insets(0, 0, 0, 0)
    isOpaque = false

    preferredSize = Dimension(48, 36)
    font = Font("Arial", Font.BOLD, font.size)
    toolTipText = tooltipText

    if (callback != null) {
      if (!requireDoubleClick) {
        handler = ActionListener { callback.invoke() }
      } else {
        handler = ActionListener {
          val now = System.currentTimeMillis()

          if (now - lastClickTime < 350) {
            callback.invoke()
          }

          lastClickTime = now
        }
      }

      addActionListener(handler)
    }
  }

  override fun onBeforeDestroy() = removeActionListener(handler)
}