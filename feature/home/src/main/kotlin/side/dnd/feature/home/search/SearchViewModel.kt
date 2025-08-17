package side.dnd.feature.home.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {
    val searchUiState: StateFlow<SearchUiState> field: MutableStateFlow<SearchUiState> = MutableStateFlow(
        SearchUiState.EMPTY.copy(
            categories = SearchUiState.mockCategories,
            selectedCategories = linkedMapOf("최상위" to "ㅁㅁ")
        )
    )

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

            is SearchEvent.SwitchPage -> TODO()
        }
    }
}