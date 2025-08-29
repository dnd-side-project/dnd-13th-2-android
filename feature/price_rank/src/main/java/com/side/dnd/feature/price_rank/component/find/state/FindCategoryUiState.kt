package com.side.dnd.feature.price_rank.component.find.state

import androidx.compose.foundation.text.input.TextFieldState
import com.side.dnd.feature.price_rank.navigation.PriceRankNavigationAction
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import side.dnd.core.SnackBarMessage

data class FindCategoryUiState(
    val searchQuery: TextFieldState,
    val searchResult: ImmutableList<Product>,
) {
    companion object {
        val EMPTY = FindCategoryUiState(
            searchQuery = TextFieldState(""),
            searchResult = persistentListOf()
        )
    }
}

data class Product(
    val id: Int,
    val name: String,
)

data class SearchCategory(
    val title: String,
    val icon: Int
)

sealed class FindCategoryEvent {
    data class SearchProduct(val searchQuery: String) : FindCategoryEvent()
    data class SearchFiltering(val id: Int, val name: String) : FindCategoryEvent()
    data class OnSearchClicked(val searchQuery: String) : FindCategoryEvent()
}

sealed class FindCategorySideEffect {
    data class Navigation(val navAction: PriceRankNavigationAction) : FindCategorySideEffect()
    data class ShowSnackBar(val message: SnackBarMessage) : FindCategorySideEffect()
}