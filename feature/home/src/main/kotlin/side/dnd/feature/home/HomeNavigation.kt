package side.dnd.feature.home

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import side.dnd.core.TopLevelRoute
import side.dnd.core.compositionLocals.LocalAnimatedContentScope
import side.dnd.core.compositionLocals.NavigationAction
import side.dnd.feature.home.home.HomeScreen
import side.dnd.feature.home.home.UserMapState
import side.dnd.feature.home.search.SearchScreen
import side.dnd.feature.home.store.StoreScreen

@Serializable
sealed class HomeRoute {
    @Serializable
    object HomeGraph : HomeRoute()

    @Serializable
    data object Home : HomeRoute(), TopLevelRoute {
        override val icon: Int
            get() = side.dnd.design.R.drawable.ic_home
        override val description: String
            get() = "홈"
    }

    @Serializable
    data class Search(val userMapState: UserMapState) : HomeRoute()

    @Serializable
    data class Store(val userMapState: UserMapState) : HomeRoute()
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.homeGraph() {
    navigation<HomeRoute.HomeGraph>(startDestination = HomeRoute.Home) {
        composable<HomeRoute.Home> {
            CompositionLocalProvider(LocalAnimatedContentScope provides this) {
                HomeScreen()
            }
        }

        composable<HomeRoute.Search>(
            typeMap = UserMapState.navType
        ) {
            CompositionLocalProvider(LocalAnimatedContentScope provides this) {
                SearchScreen()
            }
        }

        composable<HomeRoute.Store>(
            typeMap = UserMapState.navType
        ) {
            CompositionLocalProvider(LocalAnimatedContentScope provides this) {
                StoreScreen()
            }
        }
    }
}

fun NavController.navigateToSearch(userMapState: UserMapState) {
    navigate(HomeRoute.Search(userMapState))
}

fun NavController.navigateToHome() {
    popBackStack(HomeRoute.Home, inclusive = false)
}

fun NavController.navigateToStore(userMapState: UserMapState) {
    navigate(HomeRoute.Store(userMapState = userMapState))
}

sealed class HomeNavigationAction : NavigationAction {
    data class NavigateToSearch(val userMapState: UserMapState) : HomeNavigationAction()
    data object NavigateToHome : HomeNavigationAction()
    data class NavigateToStore(val userMapState: UserMapState) : HomeNavigationAction()
}