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
import side.dnd.feature.home.search.SearchScreen
import side.dnd.feature.home.store.StoreScreen

@Serializable
sealed class HomeRoute {
    @Serializable
    object HomeGraph : HomeRoute()

    @Serializable
    data class Home(val searchWord: String) : HomeRoute(), TopLevelRoute {
        override val icon: Int
            get() = side.dnd.design.R.drawable.ic_home
        override val description: String
            get() = "홈"
    }

    @Serializable
    data object Search : HomeRoute()

    @Serializable
    data class Store(val searchWord: String) : HomeRoute()
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.homeGraph() {
    navigation<HomeRoute.HomeGraph>(startDestination = HomeRoute.Home("")) {
        composable<HomeRoute.Home> {
            CompositionLocalProvider(LocalAnimatedContentScope provides this) {
                HomeScreen()
            }
        }

        composable<HomeRoute.Search> {
            CompositionLocalProvider(LocalAnimatedContentScope provides this) {
                SearchScreen()
            }
        }

        composable<HomeRoute.Store> {
            CompositionLocalProvider(LocalAnimatedContentScope provides this) {
                StoreScreen()
            }
        }
    }
}

fun NavController.navigateToSearch() {
    navigate(HomeRoute.Search)
}

fun NavController.navigateToHome(searchWord: String) {
    navigate(route = HomeRoute.Home(searchWord)) {
        popUpTo(HomeRoute.HomeGraph)
    }
}

fun NavController.navigateToStore(searchWord: String) {
    navigate(HomeRoute.Store(searchWord))
}

sealed class HomeNavigationAction : NavigationAction {
    data object NavigateToSearch : HomeNavigationAction()
    data class NavigateToHome(val searchWord: String) : HomeNavigationAction()
    data class NavigateToStore(val searchWord: String) : HomeNavigationAction()
}