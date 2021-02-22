package com.itemcarts.ui

/** used to cleanup/detach ui listeners */
interface Destroyable {
  fun onBeforeDestroy()
}