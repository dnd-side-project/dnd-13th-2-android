package com.side.dnd.feature.price_rank.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.side.dnd.feature.price_rank.PriceRankScreen
import com.side.dnd.feature.price_rank.component.find.FindCategoryScreen
import kotlinx.serialization.Serializable
import side.dnd.core.TopLevelRoute
import side.dnd.core.compositionLocals.LocalAnimatedContentScope
import side.dnd.core.compositionLocals.NavigationAction

@Serializable
sealed class PriceRankRoute {
    @Serializable
    object PriceRankGraph : PriceRankRoute()

    @Serializable
    data class PriceRank(
        val productId: Int = 0,
        val productName: String = ""
    ) : PriceRankRoute(), TopLevelRoute {
        override val icon: Int
            get() = side.dnd.design.R.drawable.ic_search_ranking
        override val description: String
            get() = "가격 탐색"
    }

    @Serializable
    data object CategorySearch : PriceRankRoute()
    
    @Serializable
    data class DetailCategory(
        val code: String = "",
        val name: String = ""
    ) : PriceRankRoute()
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.priceRankGraph() {
    navigation<PriceRankRoute.PriceRankGraph>(startDestination = PriceRankRoute.PriceRank()) {
        composable<PriceRankRoute.PriceRank> { navBackStackEntry ->
            val argument = navBackStackEntry.toRoute<PriceRankRoute.PriceRank>()
            CompositionLocalProvider(LocalAnimatedContentScope provides this) {
                PriceRankScreen(
                    productId = argument.productId,
                    productName = argument.productName
                )
            }
        }

        composable<PriceRankRoute.CategorySearch> {
            CompositionLocalProvider(LocalAnimatedContentScope provides this) {
                FindCategoryScreen()
            }
        }
    }
}

fun NavController.navigateToCategorySearch() {
    navigate(PriceRankRoute.CategorySearch)
}

fun NavController.navigateToDetailCategory(categoryCode: String, categoryName: String) {
    navigate(PriceRankRoute.DetailCategory(code = categoryCode, name = categoryName))
}

fun NavController.navigateToPriceRank(id: Int, name: String) {
    navigate(PriceRankRoute.PriceRank(productId = id, productName = name)) {
        popUpTo<PriceRankRoute.PriceRank> {
            inclusive = true
        }
    }
}

sealed class PriceRankNavigationAction : NavigationAction {
    data object NavigateToCategorySearch : PriceRankNavigationAction()
    data class NavigateToDetailCategory(val code: String, val name: String) : PriceRankNavigationAction()
    data class NavigateToPriceRank(val id: Int, val name: String) : PriceRankNavigationAction()
}