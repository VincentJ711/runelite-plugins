package com.itemcarts.haha.ui

/** used to cleanup/detach ui listeners */
interface Destroyable {
  fun onBeforeDestroy()
}