package com.itemcarts.ui.mainview

import com.itemcarts.IViewsCtrl
import com.itemcarts.Model
import com.itemcarts.haha.ui.Destroyable
import javax.swing.BoxLayout
import javax.swing.JPanel

class CartsPanel(private val ctrl: IViewsCtrl) : JPanel(), Destroyable {
  // a component is displayed on this panel iff it is in this map
  private var components = LinkedHashMap<String, CartComponent>()

  init {
    layout = BoxLayout(this, BoxLayout.Y_AXIS)
  }

  override fun onBeforeDestroy() = components.values.forEach {
    it.onBeforeDestroy()
  }

  fun refresh(model: Model) {
    val carts = model.carts
    val newComponents = mutableMapOf<String, CartComponent>()
    val nextCartIds = mutableSetOf<String>()

    // make sure a component exists and is up to date for each cart in the model
    for (c in carts) {
      val currComponent = components[c.uid]
      nextCartIds.add(c.uid)

      if (currComponent != null) {
        if (currComponent.shouldRefresh(c)) {
          currComponent.refresh(c)
        }
      } else {
        newComponents[c.uid] = CartComponent(c, ctrl)
      }
    }

    // delete any that are no longer in the model
    val itr = components.entries.iterator()
    while (itr.hasNext()) {
      val entry = itr.next()
      if (entry.key !in nextCartIds) {
        remove(entry.value)
        entry.value.onBeforeDestroy()
        itr.remove()
      }
    }

    // determine if cart order has changed
    var orderChanged = false
    var pos = 0
    for ((uid, _) in components) {
      if (carts[pos++].uid != uid) {
        orderChanged = true
        break
      }
    }

    if (orderChanged || newComponents.isNotEmpty()) {
      val nextComponents = LinkedHashMap<String, CartComponent>()

      removeAll()

      for (cart in carts) {
        val uid = cart.uid
        val component = (components[uid] ?: newComponents[uid])!!
        nextComponents[uid] = component
        add(component)
      }

      components = nextComponents
    }
  }
}