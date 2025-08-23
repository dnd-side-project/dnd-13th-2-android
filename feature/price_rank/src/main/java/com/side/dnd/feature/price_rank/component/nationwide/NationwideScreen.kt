package com.side.dnd.feature.price_rank.component.nationwide

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.side.dnd.feature.price_rank.model.NationWideUiState
import side.dnd.design.theme.EodigoColor


@Composable
fun NationwideScreen(
    uiState: NationWideUiState
) {
    val pagerState = rememberPagerState(pageCount = { 2 })

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(EodigoColor.White)
    ) {
        HorizontalPager(
            state = pagerState,
//            modifier = Modifier.weight(1f)
        ) { page ->
            when (page) {
                0 -> PriceMapScreen(
                    isEmptyKeyword = uiState.keyWord.isEmpty(),
                    productRanking = uiState.productRanking,
                )

                1 -> PriceChartScreen(
                    chartData = uiState.chartData
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) EodigoColor.Normal else EodigoColor.Gray300
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(6.dp)
                        .background(color, CircleShape)
                )
            }
        }
    }
}
