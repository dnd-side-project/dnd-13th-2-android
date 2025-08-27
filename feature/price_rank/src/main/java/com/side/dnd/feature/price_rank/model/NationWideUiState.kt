package com.side.dnd.feature.price_rank.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
data class NationWideUiState(
    val keyWord: String,
    val productRanking: ProductRanking,
    val chartData: ProductChartData,
    val isEmptyKeyword: Boolean = true
) {
   companion object {
       val Empty = NationWideUiState(
           keyWord = "",
           productRanking = MockProductRanking.sampleProductRanking,
           chartData = MockProductChartData.sampleChartData,
           isEmptyKeyword = true
       )
   }
}

