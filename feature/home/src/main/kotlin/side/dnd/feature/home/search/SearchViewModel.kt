package side.dnd.feature.home.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import side.dnd.feature.home.HomeNavigationAction
import side.dnd.feature.home.search.SearchSideEffect.Navigate
import side.dnd.feature.home.search.SearchSideEffect.SwitchPage
import side.dnd.feature.home.search.SearchUiState.Companion.TOP_CATEGORY
import side.dnd.feature.home.search.SearchUiState.Companion.defaultCategory
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {
    val searchUiState: StateFlow<SearchUiState> field: MutableStateFlow<SearchUiState> = MutableStateFlow(
        SearchUiState.EMPTY.copy(
            categories = SearchUiState.mockCategories,
        )
    )

    val sideEffect: Channel<SearchSideEffect> = Channel()

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnSelectChip -> {
                searchUiState.update { state ->
                    state.copy(
                        selectedCategories = state.selectedCategories.apply {
                            if (state.selectedCategories.containsKey(event.mainCategory)) {
                                repeat(keys.size - keys.indexOf(event.mainCategory) - 1) {
                                    remove(keys.last())
                                }
                            }

                            put(event.mainCategory, event.subCategory)
                        },
                        categoryPointer = event.subCategory
                    )
                }
            }

            is SearchEvent.SwitchPage -> {
                viewModelScope.launch {
                    searchUiState.update { state ->
                        state.copy(selectedTab = event.searchTab)
                    }
                    sideEffect.send(SwitchPage(event.searchTab.ordinal))
                }
            }

            SearchEvent.onBrowse -> {
                viewModelScope.launch {
                    sideEffect.send(Navigate(HomeNavigationAction.NavigateToHome))
                }
            }

            SearchEvent.ResetSelectedCategories -> {
                searchUiState.update { state ->
                    state.copy(
                        selectedCategories = defaultCategory,
                        categoryPointer = TOP_CATEGORY,
                    )
                }
            }
        }
    }
}