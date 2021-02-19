package com.itemcarts.haha.ui

import com.itemcarts.haha.Cart
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
  /** takes you to the carts view */
  fun goToCartsView()

  /** takes you to the summary view */
  fun goToSummaryView()

  /** takes you to the cart view to edit the given cart */
  fun goToEditCartView(cart: Cart)

  /** takes you to the cart view to create a new cart */
  fun goToAddCartView()

  /** repaints the root panel (most likely the plugin panel) */
  fun repaint()
}

class UiManager private constructor(
  private val rootPanel: JPanel,
  private val modelManager: ModelManager,
  private val cartsViewManager: CartsViewManager,
  private val summaryViewManager: SummaryViewManager,
  private val cartViewManager: CartViewManager
) :
  IUiManager,
  ICartsViewManager by cartsViewManager,
  ISummaryViewManager by summaryViewManager,
  ICartViewManager by cartViewManager {

  constructor(rootPanel: JPanel, modelManager: ModelManager) : this(
    rootPanel,
    modelManager,
    CartsViewManager(modelManager),
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
  }

  override fun goToCartsView() = ontoEDT {
    TODO("Not yet implemented")
  }

  override fun goToSummaryView() = ontoEDT {
    TODO("Not yet implemented")
  }

  override fun goToEditCartView(cart: Cart) = ontoEDT {
    TODO("Not yet implemented")
  }

  override fun goToAddCartView() {
    TODO("Not yet implemented")
  }

  override fun repaint() = ontoEDT {
    rootPanel.revalidate()
    rootPanel.repaint()
  }

  private fun setView(view: View) = ontoEDT {
    cartsViewManager.rootPanel.isVisible = view == View.CARTS
    summaryViewManager.rootPanel.isVisible = view == View.SUMMARY
    cartViewManager.rootPanel.isVisible = view == View.EDIT_CART
    repaint()
  }
}