package side.dnd.feature.home.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import side.dnd.core.compositionLocals.CommonNavigationAction
import side.dnd.feature.home.HomeNavigationAction
import side.dnd.feature.home.search.SearchSideEffect.Navigate
import side.dnd.feature.home.search.SearchSideEffect.SwitchPage
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {
    val searchUiState: StateFlow<SearchUiState> field: MutableStateFlow<SearchUiState> = MutableStateFlow(
        SearchUiState.EMPTY
    )

    val sideEffect: Channel<SearchSideEffect> = Channel()

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnSelectChip -> {
                searchUiState.update { state ->
                    state.copy(selectedCategory = event.selectedCategory)
                }
            }

            is SearchEvent.SwitchPage -> {
                viewModelScope.launch {
                    sideEffect.send(SwitchPage(event.storeType.ordinal))
                }
            }

            is SearchEvent.onBrowse -> {
                //TODO 검색어로 위치 기반 가게 리스트 api 요청
                viewModelScope.launch {
                    sideEffect.send(Navigate(HomeNavigationAction.NavigateToHome(event.searchWord)))
                }
            }

            SearchEvent.ResetSelectedCategories -> {
                searchUiState.update { state ->
                    state.copy(selectedCategory = "")
                }
            }

            SearchEvent.PopBackStack -> {
                viewModelScope.launch {
                    sideEffect.send(Navigate(CommonNavigationAction.PopBackStack))
                }
            }
        }
    }
}