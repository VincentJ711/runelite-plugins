package com.itemcarts.haha.ui.cartview

import com.itemcarts.haha.ui.UiManager
import javax.swing.JPanel

interface ICartViewManager

/** used for modifying/creating a single cart */
class CartViewManager : ICartViewManager {
  lateinit var uiManager: UiManager
  val rootPanel = JPanel()
}