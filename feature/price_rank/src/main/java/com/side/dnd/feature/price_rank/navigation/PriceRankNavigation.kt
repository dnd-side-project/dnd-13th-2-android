package com.side.dnd.feature.price_rank.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
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
    data object PriceRank : PriceRankRoute(), TopLevelRoute {
        override val icon: Int
            get() = side.dnd.design.R.drawable.ic_price_ranking
        override val description: String
            get() = "가격 랭킹"
    }

    @Serializable
    data object CategorySearch : PriceRankRoute()
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.priceRankGraph() {
    navigation<PriceRankRoute.PriceRankGraph>(startDestination = PriceRankRoute.PriceRank) {
        composable<PriceRankRoute.PriceRank> {
            CompositionLocalProvider(LocalAnimatedContentScope provides this) {
                PriceRankScreen()
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

fun NavController.navigateToPriceRank() {
    navigate(PriceRankRoute.PriceRank)
}

sealed class PriceRankNavigationAction : NavigationAction {
    data object NavigateToCategorySearch : PriceRankNavigationAction()
    data object NavigateToPriceRank : PriceRankNavigationAction()
}