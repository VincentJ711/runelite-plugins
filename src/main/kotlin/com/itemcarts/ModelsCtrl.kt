package com.itemcarts

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Base64
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

typealias CartIDs = List<String>

interface IModelsCtrl {
  /** ensures a model exists for the given username */
  fun register(uname: String?)
  fun getModel(uname: String): Model?
  fun setModel(uname: String, model: Model)
  fun editCart(uname: String, cart: Cart)
  fun createCart(uname: String, cart: Cart)
  fun deleteCart(uname: String, uid: String)
  fun sortCarts(uname: String, newOrder: CartIDs)
  fun encodeModels(): String

  /**
   * @param json should be a json array of SharableCarts
   * @return if the carts were imported
   */
  fun importCarts(uname: String, json: String): Boolean
}

class ModelsCtrl(
    config: String,
    private val reconfigureCartItemCounts: () -> Unit
) : IModelsCtrl {
  private val lock = ReentrantLock()
  private val gson = Gson()
  private val models: MutableMap<String, Model> =
      if (config.isEmpty()) mutableMapOf() else {
        val json = String(Base64.getDecoder().decode(config))
        val mapType = object :
            TypeToken<MutableMap<String, Model>>() {}.type
        gson.fromJson(json, mapType)
      }

  override fun register(uname: String?) = lock.withLock {
    val un = uname?.trim()
    if (un != null && un.isNotEmpty() && models[un] == null) {
      models[un] = Model()
    }
  }

  override fun getModel(uname: String) = lock.withLock {
    models[uname]
  }

  override fun setModel(uname: String, model: Model) = lock.withLock {
    models[uname] = model
  }

  override fun editCart(uname: String, cart: Cart) {
    lock.withLock {
      val model = models[uname] ?: return@withLock
      val carts = model.carts.map { if (cart.uid == it.uid) cart else it }
      models[uname] = model.copy(carts = carts)
    }
    reconfigureCartItemCounts()
  }

  override fun createCart(uname: String, cart: Cart) {
    lock.withLock {
      val model = models[uname] ?: return@withLock
      val carts = listOf(cart) + model.carts
      models[uname] = model.copy(carts = carts)
    }
    reconfigureCartItemCounts()
  }

  override fun deleteCart(uname: String, uid: String) {
    lock.withLock {
      val model = models[uname] ?: return@withLock
      val carts = model.carts.filter { uid != it.uid }
      models[uname] = model.copy(carts = carts)
    }
    reconfigureCartItemCounts()
  }

  override fun sortCarts(uname: String, newOrder: CartIDs) {
    lock.withLock {
      val model = models[uname] ?: return@withLock
      val map = mutableMapOf<String, Int>()
      newOrder.forEachIndexed { ind, uid -> map[uid] = ind }
      val carts = model.carts.sortedBy { map[it.uid] ?: Integer.MAX_VALUE }
      models[uname] = model.copy(carts = carts)
    }
    reconfigureCartItemCounts()
  }

  override fun encodeModels(): String = lock.withLock {
    val json = gson.toJson(models)
    String(Base64.getEncoder().encode(json.toByteArray()))
  }

  override fun importCarts(uname: String, json: String): Boolean {
    val ltype = object : TypeToken<List<ShareableCart>>() {}.type

    try {
      val scarts: List<ShareableCart> = gson.fromJson(json, ltype)
      val carts: List<Cart> = scarts.map { scart -> Cart(scart) }

      lock.withLock {
        val model = models[uname] ?: return@withLock
        models[uname] = model.copy(carts = carts + model.carts)
      }

      reconfigureCartItemCounts()
      return true
    } catch (e: Throwable) {
      return false
    }
  }
}