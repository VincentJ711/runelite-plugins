package com.itemcarts.ui

import com.itemcarts.Cart
import com.itemcarts.ontoEDT
import com.itemcarts.ui.cartsview.CartsViewManager
import com.itemcarts.ui.cartsview.ICartsViewManager
import com.itemcarts.ui.cartview.CartViewManager
import com.itemcarts.ui.cartview.ICartViewManager
import com.itemcarts.ui.summaryview.ISummaryViewManager
import com.itemcarts.ui.summaryview.SummaryViewManager
import java.awt.BorderLayout
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
    pluginPanel.add(cartsViewManager.rootPanel, BorderLayout.CENTER)
    // pluginPanel.add(summaryViewManager.rootPanel, BorderLayout.CENTER)
    // pluginPanel.add(cartViewManager.rootPanel, BorderLayout.CENTER)
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