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

@Serializable
sealed class HomeRoute {
    @Serializable
    object HomeGraph : HomeRoute()

    @Serializable
    data object Home: HomeRoute(), TopLevelRoute {
        override val icon: Int
            get() = side.dnd.design.R.drawable.ic_home
        override val description: String
            get() = "홈"
    }

    @Serializable
    data object Search: HomeRoute()
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.homeGraph() {
    navigation<HomeRoute.HomeGraph>(startDestination = HomeRoute.Home) {
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
    }
}

fun NavController.navigateToSearch() {
    navigate(HomeRoute.Search)
}

fun NavController.navigateToHome() {
    navigate(HomeRoute.Home)
}

sealed class HomeNavigationAction: NavigationAction {
    data object NavigateToSearch : HomeNavigationAction()
    data object NavigateToHome : HomeNavigationAction()
    data object PopBackStack: NavigationAction
}