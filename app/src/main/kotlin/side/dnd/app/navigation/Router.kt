package side.dnd.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import side.dnd.core.TopLevelRoute
import side.dnd.core.compositionLocals.NavigationAction
import side.dnd.feature.home.HomeNavigationAction
import side.dnd.feature.home.HomeRoute
import side.dnd.feature.home.navigateToHome
import side.dnd.feature.home.navigateToSearch

internal val TopLevelRoutes: List<TopLevelRoute> = listOf(
    HomeRoute.Home
)

@Composable
internal fun rememberRouter(navController: NavHostController) =
    remember(navController) { Router(navController) }

/**
 * Navigation을 담당하는 클래스
 * @param navController navigation을 수행하는 주체
 */
@Stable
internal class Router(val navController: NavHostController) {

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    internal fun navigateTopLevelDestination(topLevelRoute: TopLevelRoute) {
        val navOptions = navOptions {
            navController.currentBackStackEntry?.destination?.route?.let {
                popUpTo(it) {
                    inclusive = true
                }
            }
            launchSingleTop = true
        }

        when (topLevelRoute) {
            //TODO feature 모듈 생성 후 navigate 작성
            is HomeRoute.Home -> navController.navigate(HomeRoute.Home, navOptions)
        }
    }

    fun navigate(action: NavigationAction) {
        when(action) {
            is HomeNavigationAction.PopBackStack -> navController.popBackStackIfCan()
            is HomeNavigationAction.NavigateToHome -> navController.navigateToHome()
            is HomeNavigationAction.NavigateToSearch -> navController.navigateToSearch()
        }
    }

}

fun NavDestination?.isBarHasToBeShown(): Boolean =
    this?.let {
        TopLevelRoutes.any { topLevelRoute -> hasRoute(route = topLevelRoute::class) }
    } == true

fun <T: Any> NavDestination?.isDestinationInHierarchy(destination: T) =
    this?.hierarchy?.any {
        it.hasRoute(destination::class)
    } == true

fun NavController.popBackStackIfCan() {
    this.previousBackStackEntry?.let {
        this.popBackStack()
    }
}