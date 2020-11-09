package com.itemcarts

import java.rmi.server.UID
import kotlin.math.min

/*
  to config: gson read/write via Model
  import: gson read via ShareableModel
  export: gson write via ShareableModel
 */

class ShareableModel(
    val carts: List<ShareableCart>
)

class ShareableCart(
    val name: String,
    val items: List<ShareableCartItem>,
    val tags: List<String>
)

class ShareableCartItem(
    val name: String,
    val target: Long
)

data class Model(
    val carts: List<Cart> = emptyList(),
    val itemsOnPlayer: Map<String, Long> = emptyMap(),
    val itemsInBank: Map<String, Long> = emptyMap()
)

data class Cart(
    val name: String,
    val items: List<CartItem>,
    val tags: List<String>,
    val expanded: Boolean,
    val uid: String = "${UID()}"
) {

  constructor(scart: ShareableCart) : this(
      name = scart.name,
      expanded = true,
      tags = scart.tags,
      items = scart.items.map { item ->
        CartItem(
            name = item.name,
            target = item.target,
            splitAmt = 0,
            actualAmt = 0
        )
      }
  )

  fun getProgress() = getItemProgress()

  private fun getItemProgress(): CartProgress {
    var remainingWeight = 100
    val baseWeight = 100 / items.size
    var splitPercent = 0
    var actualPercent = 0
    val weights = Array(items.size) {
      remainingWeight -= baseWeight
      baseWeight
    }.map { it + (if (remainingWeight-- > 0) 1 else 0) }

    items.forEachIndexed { index, i ->
      if (i.target > 0) {
        val splitMultiplier = min(i.splitAmt, i.target).toFloat() / i.target
        val actualMultiplier = min(i.actualAmt, i.target).toFloat() / i.target
        splitPercent += (splitMultiplier * weights[index]).toInt()
        actualPercent += (actualMultiplier * weights[index]).toInt()
      }
    }

    return CartProgress(
        splitPercent = splitPercent,
        actualPercent = actualPercent
    )
  }
}

data class CartItem(
    val name: String,
    val target: Long,

    /**
     * if there are n cart items for this item, then this will be roughly
     * equal to floor(n / splitAmt)
     */
    val splitAmt: Long,

    /** how much of this item the player currently has */
    val actualAmt: Long
)

data class CartProgress(
    /** [-1,100]. -1 if there were no items in the cart */
    val splitPercent: Int,

    /** [-1,100]. -1 if there were no items in the cart */
    val actualPercent: Int
) {
  val splitComplete = listOf(splitPercent).all { it == -1 || it == 100 }
  val actualComplete = listOf(actualPercent).all { it == -1 || it == 100 }
}