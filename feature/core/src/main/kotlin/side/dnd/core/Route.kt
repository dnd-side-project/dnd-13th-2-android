package side.dnd.core

import androidx.annotation.DrawableRes

interface TopLevelRoute {
    @get:DrawableRes
    val icon: Int

    val description: String
}
