package com.itemcarts.haha

import kotlin.test.Test
import kotlin.test.assertEquals

class UtilTest {
  @Test
  fun calcRequiredAmtTest() {
    assertEquals(
      1, calcRequiredAmt(
        mutableListOf(
          CartItem("a", reusableAmt = 1, consumableAmt = 0, currentAmt = 0),
          CartItem("a", reusableAmt = 1, consumableAmt = 0, currentAmt = 0)
        )
      )
    )

    assertEquals(
      11, calcRequiredAmt(
        mutableListOf(
          CartItem("a", reusableAmt = 3, consumableAmt = 2, currentAmt = 0),
          CartItem("a", reusableAmt = 2, consumableAmt = 4, currentAmt = 0),
          CartItem("a", reusableAmt = 3, consumableAmt = 1, currentAmt = 0),
          CartItem("a", reusableAmt = 0, consumableAmt = 4, currentAmt = 0)
        )
      )
    )

    // switch the order. shows why placing higher reusable amts lower results
    // in larger required amounts
    assertEquals(
      14, calcRequiredAmt(
        mutableListOf(
          CartItem("a", reusableAmt = 0, consumableAmt = 4, currentAmt = 0),
          CartItem("a", reusableAmt = 2, consumableAmt = 4, currentAmt = 0),
          CartItem("a", reusableAmt = 3, consumableAmt = 1, currentAmt = 0),
          CartItem("a", reusableAmt = 3, consumableAmt = 2, currentAmt = 0)
        )
      )
    )
  }

  @Test
  fun calcSummaryTest() {
    val carts = mutableListOf<Cart>()

    carts.addAll(
      listOf(
        Cart(
          "cart1",
          mutableListOf(
            CartItem(
              "whip",
              reusableAmt = 3,
              consumableAmt = 2,
              currentAmt = 0
            ),
            CartItem(
              "scim",
              reusableAmt = 0,
              consumableAmt = 2,
              currentAmt = 5
            )
          ),
          false
        ),
        Cart(
          "cart2",
          mutableListOf(
            CartItem(
              "whip",
              reusableAmt = 0,
              consumableAmt = 20,
              currentAmt = 0
            )
          ),
          false
        )
      )
    )

    val res = calcSummary(carts)

    assertEquals(
      listOf(
        SummaryItem("whip", 0, 22),
        SummaryItem("scim", 5, 2)
      ), res.values.toList()
    )

    println(res)
  }
}