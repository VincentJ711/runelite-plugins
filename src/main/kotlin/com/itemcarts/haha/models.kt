package com.itemcarts.haha

import java.util.UUID

data class RawCartItem(
  val name: String,
  val reusableAmt: Long,
  val consumableAmt: Long
)

data class CartItem(
  val name: String,
  val reusableAmt: Long,
  val consumableAmt: Long,
  val currentAmt: Long
) {
  val requiredAmt = reusableAmt + consumableAmt
  val completed = currentAmt >= requiredAmt
}

data class SummaryItem(
  val name: String,
  val currentAmt: Long,
  val requiredAmt: Long
) {
  val completed = currentAmt >= requiredAmt
}

data class RawCart(
  val name: String,
  val items: List<RawCartItem>
)

data class Cart(
  val name: String,
  val items: List<CartItem>,
  val uid: String = "${UUID.randomUUID()}"
) {
  val completed = items.all { it.completed }

  init {
    // TODO make sure each item name is unique
  }
}

class CartsInfo(
  val list: List<Cart>
) {
  val summary = calcSummary(list)

  override fun toString() = "CartsInfo(list=${list}, summary=${summary})"
}