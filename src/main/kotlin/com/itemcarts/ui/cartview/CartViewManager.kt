package com.itemcarts.ui.cartview

import com.itemcarts.ui.ViewManager
import javax.swing.JPanel

interface ICartViewManager

/** used for modifying/creating a single cart */
class CartViewManager : ICartViewManager {
  lateinit var viewManager: ViewManager
  val rootPanel = JPanel()
}