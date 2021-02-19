package com.itemcarts.haha.ui

import com.itemcarts.haha.Cart
import com.itemcarts.haha.ontoEDT
import com.itemcarts.haha.ui.cartsview.CartsViewManager
import com.itemcarts.haha.ui.cartsview.ICartsViewManager
import com.itemcarts.haha.ui.cartview.CartViewManager
import com.itemcarts.haha.ui.cartview.ICartViewManager
import com.itemcarts.haha.ui.summaryview.ISummaryViewManager
import com.itemcarts.haha.ui.summaryview.SummaryViewManager
import javax.inject.Inject
import javax.inject.Singleton

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

  /** repaints the root plugin panel */
  fun repaint()
}

@Singleton
class UiManager @Inject constructor(
  private val pluginPanel: ItemCartsPluginPanel,
  private val cartsViewManager: CartsViewManager,
  private val summaryViewManager: SummaryViewManager,
  private val cartViewManager: CartViewManager
) :
  IUiManager,
  ICartsViewManager by cartsViewManager,
  ISummaryViewManager by summaryViewManager,
  ICartViewManager by cartViewManager {

  init {
    setView(View.CARTS)
    pluginPanel.add(cartsViewManager.rootPanel)
    pluginPanel.add(summaryViewManager.rootPanel)
    pluginPanel.add(cartViewManager.rootPanel)
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

  override fun goToAddCartView() = ontoEDT {
    TODO("Not yet implemented")
  }

  override fun repaint() = ontoEDT {
    pluginPanel.revalidate()
    pluginPanel.repaint()
  }

  private fun setView(view: View) = ontoEDT {
    cartsViewManager.rootPanel.isVisible = view == View.CARTS
    summaryViewManager.rootPanel.isVisible = view == View.SUMMARY
    cartViewManager.rootPanel.isVisible = view == View.EDIT_CART
    repaint()
  }
}