package com.itemcarts

import net.runelite.client.config.Config
import net.runelite.client.config.ConfigGroup
import net.runelite.client.config.ConfigItem

@ConfigGroup("itemcarts")
interface ItemCartsConfig : Config {
  @ConfigItem(
      keyName = "models",
      name = "",
      description = "",
      hidden = true
  )
  @JvmDefault
  fun models() = ""

  @ConfigItem(
      keyName = "models",
      name = "",
      description = "",
      hidden = true
  )
  fun models(str: String)
}