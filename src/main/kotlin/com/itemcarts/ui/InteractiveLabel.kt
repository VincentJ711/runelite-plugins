package com.itemcarts.ui

import com.itemcarts.haha.ui.Destroyable
import net.runelite.client.ui.ColorScheme
import java.awt.Color
import java.awt.Font
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JLabel
import javax.swing.SwingConstants

class InteractiveLabel(
    text: String,
    private val uid: String,
    private val idleColor: Color = ColorScheme.BRAND_ORANGE,
    private val hoverColor: Color = ColorScheme.BRAND_ORANGE_TRANSPARENT,
    private val onMousePress: () -> Unit
) : JLabel(text, SwingConstants.CENTER), Destroyable {
  companion object {
    val unicodeFont = Font("Arial", Font.BOLD, 14)
    private val lastPressTimes = mutableMapOf<String, Long>()
  }

  private val mouseAdapter = Adapter()

  init {
    addMouseListener(mouseAdapter)
    refresh(false)
  }

  private fun refresh(hovering: Boolean) {
    val now = System.currentTimeMillis()
    val lastPressTime = lastPressTimes[uid] ?: 0

    // handles case where u press this label and this label gets regenerated
    // and the hover color disappears even though you're still hovered over it.
    val recentPress = (now - lastPressTime) < 100
    foreground = if (hovering || recentPress) hoverColor else idleColor
  }

  override fun onBeforeDestroy() = removeMouseListener(mouseAdapter)

  /** does not revalidate/refresh the ui */
  fun setFontSize(size: Int) {
    font = Font("Arial", Font.BOLD, size)
  }

  private inner class Adapter : MouseAdapter() {
    override fun mousePressed(e: MouseEvent?) {
      lastPressTimes[uid] = System.currentTimeMillis()
      onMousePress()
    }

    override fun mouseEntered(e: MouseEvent?) = refresh(true)
    override fun mouseExited(e: MouseEvent?) = refresh(false)
  }
}