package com.side.dnd.feature.price_rank.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface NationWideUiState {
    @Immutable
    data object Loading : NationWideUiState

    @Immutable
    data class UiDataSuccess(
        val productRanking: ProductRanking,
        val chartData: ProductChartData
    ) : NationWideUiState
}

