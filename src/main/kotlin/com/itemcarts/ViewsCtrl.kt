package com.itemcarts

import com.itemcarts.ui.mainview.MainView
import net.runelite.client.ui.PluginPanel
import javax.swing.JPanel
import javax.swing.SwingUtilities

interface IViewsCtrl {
  fun setModel(uname: String, model: Model)
  fun toggleExpandCart(cart: Cart)
  fun editCart(cart: Cart)
  fun createCart()
  fun sortCarts()
  fun viewCarts()
  fun importCarts(json: String)
}

class ViewsCtrl(
  private val modelsCtrl: IModelsCtrl
) : PluginPanel(), IViewsCtrl {
  private var uname: String? = null
  private var model: Model? = null
  private var view = View.MAIN
  private val mainView = MainView(this)
  private val sortView = JPanel()
  private val configCartView = JPanel()

  init {
    add(mainView)
    add(sortView)
    add(configCartView)
    refresh()
  }

  override fun setModel(uname: String, model: Model) {
    this.uname = uname
    this.model = model
    refresh()
  }

  override fun toggleExpandCart(cart: Cart) {
    modelsCtrl.editCart(uname ?: return, cart.copy(expanded = !cart.expanded))
  }

  override fun editCart(cart: Cart) {
    TODO("Not yet implemented")
  }

  override fun createCart() {
    TODO("Not yet implemented")
  }

  override fun sortCarts() {
    TODO("Not yet implemented")
  }

  override fun viewCarts() {
    view = View.MAIN
    refresh()
  }

  override fun importCarts(json: String) {
    modelsCtrl.importCarts(uname ?: return, json)
  }

  private fun refresh() = ontoUiThread {
    val v = view
    val m = model

    mainView.isVisible = v == View.MAIN
    sortView.isVisible = v == View.SORT_CARTS
    configCartView.isVisible = v == View.CONFIG_CART

    if (m != null && v == View.MAIN) {
      mainView.refresh(m)
    }

    revalidate()
    repaint()
  }

  private fun ontoUiThread(cb: () -> Unit) {
    if (SwingUtilities.isEventDispatchThread()) cb() else {
      SwingUtilities.invokeAndWait(Runnable(cb))
    }
  }

  private enum class View {
    MAIN,
    SORT_CARTS,
    CONFIG_CART
  }
}