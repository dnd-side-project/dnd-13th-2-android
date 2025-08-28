package com.side.dnd.feature.price_rank

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.side.dnd.feature.price_rank.data.repository.ProductPriceRepository
import com.side.dnd.feature.price_rank.model.NationWideUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PriceRankViewModel @Inject constructor(
    private val productPriceRepository: ProductPriceRepository
) : ViewModel() {

    private val _rankUiState = MutableStateFlow<NationWideUiState>(NationWideUiState.Empty)
    val rankUiState = _rankUiState.asStateFlow()

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow get() = _errorFlow.asSharedFlow()

    private val _tabChangeFlow = MutableSharedFlow<Int>()
    val tabChangeFlow get() = _tabChangeFlow.asSharedFlow()


    fun loadProductData(productId: Int, productName: String) {
        viewModelScope.launch {
            try {
                val rankingResult = productPriceRepository.getProductRanking(productId)
                val trendResult = productPriceRepository.getProductTrend(productId)

                rankingResult.onSuccess { ranking ->
                    trendResult.onSuccess { chartData ->
                        _rankUiState.update { currentState ->
                            currentState.copy(
                                keyWord = productName,
                                productRanking = ranking,
                                chartData = chartData
                            )
                        }
                    }.onFailure { error ->
                        _rankUiState.update { currentState ->
                            currentState.copy(
                                keyWord = productName,
                                productRanking = ranking
                            )
                        }
                        _errorFlow.emit(error)
                    }
                }.onFailure { error ->
                    _errorFlow.emit(error)
                }
            } catch (e: Exception) {
                _errorFlow.emit(e)
            }
        }
    }

    fun switchToRegionTab() {
        viewModelScope.launch {
            _tabChangeFlow.emit(1)
        }
    }

}