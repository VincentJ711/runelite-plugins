package com.itemcarts.haha

import com.itemcarts.haha.ui.UiManager
import javax.inject.Inject
import javax.inject.Singleton

interface IModelManager {
  fun updateItems(onPlayer: Map<String, Long>, inBank: Map<String, Long>)
  fun setCarts(carts: Iterable<RawCart>)
  fun updateCarts(carts: Iterable<Cart>)
  fun removeCart(uid: String)
  fun addCarts(carts: Iterable<RawCart>)
}

@Singleton
class ModelManager : IModelManager {
  @Inject
  private lateinit var uiManager: UiManager
  private var itemsInBank = mapOf<String, Long>()
  private var itemsOnPlayer = mapOf<String, Long>()
  private var summary = mapOf<String, SummaryItem>()
  var carts = listOf<Cart>()
    private set

  override fun updateItems(
    onPlayer: Map<String, Long>,
    inBank: Map<String, Long>
  ) {
    itemsOnPlayer = onPlayer
    itemsInBank = inBank

    val cartsToUpdate = carts.filter { cart ->
      cart.items.forEach { cartItem ->
        if (cartItem.currentAmt != getCurrentAmount(cartItem.name)) {
          return@filter true
        }
      }
      false
    }

    if (cartsToUpdate.isNotEmpty()) {
      updateCarts(cartsToUpdate)
    }
  }

  override fun setCarts(carts: Iterable<RawCart>) {
    this.carts = carts.map { fromRawCart(it) }
    updateSummary()
    uiManager.setCarts(this.carts)
  }

  override fun updateCarts(carts: Iterable<Cart>) {
    val updatedCarts = mutableListOf<Cart>()
    val givenCartsMap = mutableMapOf<String, Cart>()

    carts.forEach { givenCartsMap[it.uid] = it }

    this.carts = this.carts.map { cart ->
      val givenCart = givenCartsMap[cart.uid] ?: return@map cart
      val nextCart = givenCart.copy(
        items = givenCart.items.map { cartItem ->
          cartItem.copy(currentAmt = getCurrentAmount(cartItem.name))
        }
      )
      updatedCarts.add(nextCart)
      nextCart
    }

    if (updatedCarts.isNotEmpty()) {
      uiManager.updateCarts(updatedCarts)
      updateSummary()
    }
  }

  override fun removeCart(uid: String) {
    carts = carts.filter { it.uid != uid }
    uiManager.removeCart(uid)
    updateSummary()
  }

  override fun addCarts(carts: Iterable<RawCart>) {
    val cartsToAdd = carts.map { fromRawCart(it) }
    this.carts = this.carts + cartsToAdd
    uiManager.addCarts(cartsToAdd)
    updateSummary()
  }

  private fun updateSummary() {
    summary = calcSummary(carts)
    // TODO
    uiManager.repaint()
  }

  private fun fromRawCart(rawCart: RawCart): Cart {
    return Cart(
      name = rawCart.name,
      items = rawCart.items.map {
        CartItem(
          name = it.name,
          reusableAmt = it.reusableAmt,
          consumableAmt = it.consumableAmt,
          currentAmt = itemsInBank[it.name] ?: 0 + (itemsOnPlayer[it.name] ?: 0)
        )
      }
    )
  }

  private fun getCurrentAmount(item: String): Long =
    itemsInBank[item] ?: 0 + (itemsOnPlayer[item] ?: 0)
}