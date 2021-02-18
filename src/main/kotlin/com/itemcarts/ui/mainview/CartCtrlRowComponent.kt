package com.itemcarts.ui.mainview

import com.itemcarts.Cart
import com.itemcarts.IViewsCtrl
import com.itemcarts.haha.ui.Destroyable
import com.itemcarts.ui.InteractiveLabel
import net.runelite.client.ui.ColorScheme
import java.awt.BorderLayout
import java.awt.FlowLayout
import javax.swing.BorderFactory
import javax.swing.JLabel
import javax.swing.JPanel

class CartCtrlRowComponent(
    private val cart: Cart,
    private val ctrl: IViewsCtrl
) : JPanel(BorderLayout()), Destroyable {
  companion object {
    // TODO
    private const val splitLabelCboxDoneTip = "TODO"
    private const val splitLabelCboxNotDoneTip = "TODO"
    private const val actualLabelCboxDoneTip = "TODO"
    private const val actualLabelCboxNotDoneTip = "TODO"
  }

  private val pencilLabel = InteractiveLabel("✎", "$${cart.uid} 0") {
    ctrl.editCart(cart)
  }

  private val cartNameLabel = InteractiveLabel(cart.name, "${cart.uid} 1") {
    ctrl.toggleExpandCart(cart)
  }

  init {
    val pencilPanel by lazy {
      val panel = JPanel()
      panel.background = ColorScheme.DARKER_GRAY_COLOR
      panel.add(pencilLabel)
      panel
    }

    val statusBoxesPanel by lazy {
      val progress = cart.getProgress()
      val splitLabel = getCheckBox(progress.splitComplete,
          splitLabelCboxDoneTip, splitLabelCboxNotDoneTip)
      val actualLabel = getCheckBox(progress.actualComplete,
          actualLabelCboxDoneTip, actualLabelCboxNotDoneTip)
      val panel = JPanel(FlowLayout(FlowLayout.CENTER, 0, 5))
      panel.background = ColorScheme.DARKER_GRAY_COLOR
      panel.border = BorderFactory.createEmptyBorder(0, 6, 0, 6)
      panel.add(splitLabel)
      panel.add(actualLabel)
      panel
    }

    pencilLabel.font = InteractiveLabel.unicodeFont
    pencilLabel.border = BorderFactory.createEmptyBorder(0, 3, 0, 12)
    cartNameLabel.isOpaque = true
    cartNameLabel.background = ColorScheme.DARKER_GRAY_COLOR

    add(pencilPanel, BorderLayout.WEST)
    add(cartNameLabel, BorderLayout.CENTER)
    add(statusBoxesPanel, BorderLayout.EAST)
  }

  override fun onBeforeDestroy() {
    pencilLabel.onBeforeDestroy()
    cartNameLabel.onBeforeDestroy()
  }

  private fun getCheckBox(done: Boolean, doneTip: String, notDoneTip: String):
      JLabel {
    val label = JLabel(if (done) "☑" else "☐")
    label.toolTipText = if (done) doneTip else notDoneTip
    label.font = InteractiveLabel.unicodeFont
    label.foreground = if (done) ColorScheme.PROGRESS_COMPLETE_COLOR
    else ColorScheme.PROGRESS_INPROGRESS_COLOR
    return label
  }
}
