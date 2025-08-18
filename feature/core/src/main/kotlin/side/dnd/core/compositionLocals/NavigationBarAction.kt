package side.dnd.core.compositionLocals

import androidx.compose.runtime.compositionLocalOf

interface NavigationAction

sealed class CommonNavigationAction: NavigationAction {
    data object PopBackStack: CommonNavigationAction()
}

val LocalNavigationActions = compositionLocalOf<(NavigationAction) -> Unit> {
    error("Navigation actions not provided")
}