package com.side.dnd.feature.price_rank

import androidx.lifecycle.ViewModel
import com.side.dnd.feature.price_rank.model.NationWideUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PriceRankViewModel @Inject constructor(

) : ViewModel() {

    private val _rankUiState = MutableStateFlow<NationWideUiState>(NationWideUiState.Empty)
    val rankUiState = _rankUiState.asStateFlow()

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow get() = _errorFlow.asSharedFlow()

    init {
//        _rankUiState.update {
//            NationWideUiState.UiDataSuccess(
//                keyWord = "김치",
//                productRanking = MockProductRanking.sampleProductRanking,
//                chartData = MockProductChartData.sampleChartData
//            )
//        }
    }

}