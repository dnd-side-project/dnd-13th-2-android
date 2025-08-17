package side.dnd.core.compositionLocals

import androidx.compose.runtime.compositionLocalOf

interface NavigationAction

val LocalNavigationActions = compositionLocalOf<(NavigationAction) -> Unit> {
    error("Navigation actions not provided")
}