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
import javax.swing.JPanel

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
class ViewManager @Inject constructor(
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
    pluginPanel.add(cartsViewManager.rootPanel, BorderLayout.CENTER)
  }

  override fun goToCartsView() = setView(cartsViewManager.rootPanel)

  override fun goToSummaryView() = setView(summaryViewManager.rootPanel)

  override fun goToEditCartView(cart: Cart) {
    TODO("Not yet implemented")
  }

  override fun goToAddCartView() = ontoEDT {
    TODO("Not yet implemented")
  }

  override fun repaint() = ontoEDT {
    pluginPanel.revalidate()
    pluginPanel.repaint()
  }

  private fun setView(view: JPanel) = ontoEDT {
    pluginPanel.removeAll()
    pluginPanel.add(view, BorderLayout.CENTER)
    repaint()
  }
}