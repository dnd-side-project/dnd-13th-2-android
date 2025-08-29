package com.side.dnd.feature.price_rank.component.find

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.side.dnd.feature.price_rank.component.find.state.FindCategoryEvent
import com.side.dnd.feature.price_rank.component.find.state.FindCategorySideEffect
import com.side.dnd.feature.price_rank.component.find.state.FindCategorySideEffect.Navigation
import com.side.dnd.feature.price_rank.component.find.state.FindCategorySideEffect.ShowSnackBar
import com.side.dnd.feature.price_rank.component.find.state.FindCategoryUiState
import com.side.dnd.feature.price_rank.component.find.state.Product
import com.side.dnd.feature.price_rank.navigation.PriceRankNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import side.dnd.core.SnackBarMessage
import side.dnd.data.network.ProductRepository
import javax.inject.Inject

@HiltViewModel
class FindCategoryViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) : ViewModel() {

    val uiState: StateFlow<FindCategoryUiState> field: MutableStateFlow<FindCategoryUiState> = MutableStateFlow(
        FindCategoryUiState.EMPTY
    )

    val sideEffect = Channel<FindCategorySideEffect>()

    fun onEvent(event: FindCategoryEvent) {
        when (event) {
            is FindCategoryEvent.SearchProduct -> {
                viewModelScope.launch {
                    val result = searchProduct(event.searchQuery)

                    uiState.update { state ->
                        state.copy(
                            searchResult = result.map {
                                Product(
                                    id = it.productId,
                                    name = it.name,
                                )
                            }.toPersistentList()
                        )
                    }
                }
            }

            is FindCategoryEvent.SearchFiltering -> {
                viewModelScope.launch {
                    sideEffect.send(
                        Navigation(
                            PriceRankNavigationAction.NavigateToPriceRank(
                                id = event.id,
                                name = event.name
                            )
                        )
                    )
                }
            }

            is FindCategoryEvent.OnSearchClicked -> {
                uiState.value.searchResult.find {
                    event.searchQuery === it.name
                }?.let {
                    onEvent(FindCategoryEvent.SearchFiltering(id = it.id, name = it.name))
                } ?: viewModelScope.launch {
                    sideEffect.send(
                        ShowSnackBar(
                            SnackBarMessage(
                                headerMessage = "검색어와 일치하는 품목이 없어요.",
                                contentMessage = "품목을 선택 하거나, 검색어를 완전히 일치 시켜 주세요."
                            )
                        )
                    )
                }
            }
        }
    }

    private suspend fun searchProduct(query: String) = productRepository.searchProduct(query)
}