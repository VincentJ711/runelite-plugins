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

enum class View {
  CARTS,
  CART,
  SUMMARY
}

/**
 * this arose out of the neccessity for certain components to refresh their
 * colors whenever the view changes. for example, consider clicking a label
 * with a special hover color that transfers the user to another view.
 * when they come back to this view, the hover color will still be present
 * even if the mouse isnt over that component. this occurs because when the
 * view changes, the mouseExited event doesnt fire. this is a way
 * to correct that.
 */
interface ViewChangeListener {
  /**
   * called whenever the view changes and just prior to a revalidate/repaint.
   */
  fun onViewChanged(latestView: View)
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

  companion object {
    private val viewChangeListeners = mutableSetOf<ViewChangeListener>()
    fun addViewChangeListener(l: ViewChangeListener) {
      viewChangeListeners.add(l)
    }

    fun removeViewChangeListener(l: ViewChangeListener) {
      viewChangeListeners.remove(l)
    }
  }

  // private var currentView = View.SUMMARY
  private var currentView = View.CARTS

  init {
    // pluginPanel.add(summaryViewManager.rootPanel, BorderLayout.CENTER)
    pluginPanel.add(cartsViewManager.rootPanel, BorderLayout.CENTER)
  }

  override fun goToCartsView() = setView(View.CARTS)

  override fun goToSummaryView() = setView(View.SUMMARY)

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

  private fun setView(nextView: View) = ontoEDT {
    if (nextView == currentView) {
      return@ontoEDT
    }

    val panel = when (nextView) {
      View.CARTS -> cartsViewManager.rootPanel
      View.SUMMARY -> summaryViewManager.rootPanel
      else -> {
        TODO("NOT IMPLEMENTED")
      }
    }

    currentView = nextView
    pluginPanel.removeAll()
    pluginPanel.add(panel, BorderLayout.CENTER)
    viewChangeListeners.forEach { it.onViewChanged(nextView) }
    repaint()
  }
}