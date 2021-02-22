package com.itemcarts.ui.cartview

import com.itemcarts.ui.UiManager
import javax.swing.JPanel

interface ICartViewManager

/** used for modifying/creating a single cart */
class CartViewManager : ICartViewManager {
  lateinit var uiManager: UiManager
  val rootPanel = JPanel()
}