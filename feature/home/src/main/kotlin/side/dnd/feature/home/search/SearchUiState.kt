package side.dnd.feature.home.search

import androidx.compose.runtime.Stable
import side.dnd.core.compositionLocals.NavigationAction
import side.dnd.feature.home.HomeNavigationAction
import side.dnd.feature.home.state.StoreType

@Stable
data class SearchUiState(
    val isSearchReady: Boolean,
    val selectedCategory: String
) {
    val pageCount: Int = 2

    companion object {
        val EMPTY = SearchUiState(
            isSearchReady = false,
            selectedCategory = ""
        )
    }
}

sealed class SearchEvent {
    data class SwitchPage(val storeType: StoreType) : SearchEvent()
    data class OnSelectChip(val selectedCategory: String) : SearchEvent()
    data class onBrowse(val searchWord: String) : SearchEvent()
    data object ResetSelectedCategories : SearchEvent()
    data object PopBackStack : SearchEvent()
}

sealed class SearchSideEffect {
    data class SwitchPage(val page: Int) : SearchSideEffect()
    data class Navigate(val action: NavigationAction) : SearchSideEffect()
}