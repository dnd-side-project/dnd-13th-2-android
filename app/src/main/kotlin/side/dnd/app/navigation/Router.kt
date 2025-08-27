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
import com.side.dnd.feature.price_rank.navigation.PriceRankNavigationAction
import com.side.dnd.feature.price_rank.navigation.PriceRankRoute
import com.side.dnd.feature.price_rank.navigation.navigateToCategorySearch
import com.side.dnd.feature.price_rank.navigation.navigateToPriceRank
import side.dnd.core.TopLevelRoute
import side.dnd.core.compositionLocals.CommonNavigationAction
import side.dnd.core.compositionLocals.NavigationAction
import side.dnd.feature.home.HomeNavigationAction
import side.dnd.feature.home.HomeRoute
import side.dnd.feature.home.navigateToHome
import side.dnd.feature.home.navigateToSearch
import side.dnd.feature.home.navigateToStore

internal val TopLevelRoutes: List<TopLevelRoute> = listOf(
    HomeRoute.Home,
    PriceRankRoute.PriceRank
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
            is HomeRoute.Home -> navController.navigate(HomeRoute.Home, navOptions)
            is PriceRankRoute.PriceRank -> navController.navigate(PriceRankRoute.PriceRank, navOptions)
        }
    }

    fun navigate(action: NavigationAction) {
        when (action) {
            is CommonNavigationAction.PopBackStack -> navController.popBackStackIfCan()
            is HomeNavigationAction.NavigateToHome -> navController.navigateToHome()
            is HomeNavigationAction.NavigateToSearch -> navController.navigateToSearch(action.userMapState)
            is HomeNavigationAction.NavigateToStore -> navController.navigateToStore(userMapState = action.userMapState)
            is PriceRankNavigationAction.NavigateToPriceRank -> navController.navigateToPriceRank()
            is PriceRankNavigationAction.NavigateToCategorySearch -> navController.navigateToCategorySearch()
        }
    }

}

fun NavDestination?.isBarHasToBeShown(): Boolean =
    this?.let {
        TopLevelRoutes.any { topLevelRoute -> hasRoute(route = topLevelRoute::class) }
    } == true

fun <T : Any> NavDestination?.isDestinationInHierarchy(destination: T) =
    this?.hierarchy?.any {
        it.hasRoute(destination::class)
    } == true

fun NavController.popBackStackIfCan() {
    this.previousBackStackEntry?.let {
        this.popBackStack()
    }
}