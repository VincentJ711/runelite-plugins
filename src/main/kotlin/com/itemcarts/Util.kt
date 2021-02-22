package com.itemcarts

import javax.swing.SwingUtilities

/**
 * returns a map with a key for each item name in the given carts whose
 * value is an object notably containing the total required amount needed
 * for all the items in the given carts with the same name. Note that these
 * required amounts are minimized; they are not maximized.
 *
 * Also note that a current amount is placed onto each value and
 * is equal to the first given cart item's (with the matching name)
 * current amount. Therefore, this assumes rightly so that the given cart items
 * with the same name all share the same current amount.
 */
fun calcSummary(carts: Iterable<Cart>): Map<String, SummaryItem> {
  val summary = mutableMapOf<String, SummaryItem>()

  // ensure items with the highest reusableAmt's go first to minimize
  // the total required amount needed for all items with the same name
  carts
    .flatMap { it.items }
    .sortedByDescending { it.reusableAmt }
    .groupBy { it.name }
    .map { (itemName, itemsWithSameName) ->
      summary[itemName] = SummaryItem(
        itemName,
        itemsWithSameName[0].currentAmt,
        calcRequiredAmt(itemsWithSameName)
      )
    }

  return summary
}

/**
 * returns the "sum" of the required amounts for the given items.
 * Note that the sum is done in a clever way and is not a direct sum.
 *
 * The order of the given items matters for this calculation and this
 * function follows the given order. if an item
 * with a higher reusable amt is placed after an item with a
 * smaller reusable amt, then the returned value is likely to
 * be bigger than if the two items were swapped.
 */
fun calcRequiredAmt(itemsWithSameName: Collection<CartItem>): Long {
  var available = 0L
  var needed = 0L

  for (item in itemsWithSameName) {
    if (item.requiredAmt > available) {
      needed += (item.requiredAmt - available)
      available = item.reusableAmt
    } else {
      available -= item.consumableAmt
    }
  }

  return needed
}

/**
 * ensures the given callback is executed on the swing event
 * dispatch thread.
 */
fun ontoEDT(cb: () -> Unit) {
  if (SwingUtilities.isEventDispatchThread()) {
    cb()
  } else {
    SwingUtilities.invokeLater(Runnable(cb))
  }
}