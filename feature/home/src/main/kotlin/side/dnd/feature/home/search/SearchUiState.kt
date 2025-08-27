package side.dnd.feature.home.search

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Stable
import side.dnd.core.compositionLocals.NavigationAction
import side.dnd.feature.home.state.StoreType

@Stable
data class SearchUiState(
    val selectedCategory: String,
    val textFieldState: TextFieldState,
) {
    val pageCount: Int = 2

    companion object {
        val EMPTY = SearchUiState(
            selectedCategory = "",
            textFieldState = TextFieldState()
        )
    }
}

sealed class SearchEvent {
    data class SwitchPage(val storeType: StoreType) : SearchEvent()
    data class OnSelectChip(val selectedCategory: String) : SearchEvent()
    data object OnBrowse : SearchEvent()
    data object OnSearch : SearchEvent()
    data object ResetSelectedCategories : SearchEvent()
    data object PopBackStack : SearchEvent()
}

sealed class SearchSideEffect {
    data class SwitchPage(val page: Int) : SearchSideEffect()
    data class Navigate(val action: NavigationAction) : SearchSideEffect()
}