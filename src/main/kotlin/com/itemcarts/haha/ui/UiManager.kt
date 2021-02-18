package com.itemcarts.haha.ui

import com.itemcarts.haha.ModelManager
import com.itemcarts.haha.ontoEDT
import com.itemcarts.haha.ui.cartsview.CartsViewManager
import com.itemcarts.haha.ui.cartsview.ICartsViewManager
import com.itemcarts.haha.ui.cartview.CartViewManager
import com.itemcarts.haha.ui.cartview.ICartViewManager
import com.itemcarts.haha.ui.summaryview.ISummaryViewManager
import com.itemcarts.haha.ui.summaryview.SummaryViewManager
import javax.swing.JPanel

enum class View {
  CARTS,
  SUMMARY,
  EDIT_CART
}

interface IUiManager {
  fun setView(view: View)
  fun repaint()
}

class UiManager private constructor(
  private val rootPanel: JPanel,
  private val modelManager: ModelManager,
  private val cartsViewManager: CartsViewManager,
  private val summaryViewManager: SummaryViewManager,
  private val cartViewManager: CartViewManager
) : IUiManager,
  ICartsViewManager by cartsViewManager,
  ISummaryViewManager by summaryViewManager,
  ICartViewManager by cartViewManager {

  constructor(rootPanel: JPanel, modelManager: ModelManager) : this(
    rootPanel,
    modelManager,
    CartsViewManager(),
    SummaryViewManager(),
    CartViewManager()
  ) {
    cartsViewManager.uiManager = this
    summaryViewManager.uiManager = this
    cartViewManager.uiManager = this
  }

  init {
    setView(View.CARTS)
    rootPanel.add(cartsViewManager.rootPanel)
    rootPanel.add(summaryViewManager.rootPanel)
    rootPanel.add(cartViewManager.rootPanel)
    rootPanel.isOpaque = true
  }

  override fun setView(view: View) = ontoEDT {
    cartsViewManager.rootPanel.isVisible = view == View.CARTS
    summaryViewManager.rootPanel.isVisible = view == View.SUMMARY
    cartViewManager.rootPanel.isVisible = view == View.EDIT_CART
    repaint()
  }

  override fun repaint() = ontoEDT {
    rootPanel.revalidate()
    rootPanel.repaint()
  }
}