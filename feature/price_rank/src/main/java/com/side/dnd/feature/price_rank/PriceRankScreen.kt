package com.side.dnd.feature.price_rank

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.side.dnd.feature.price_rank.component.nationwide.NationwideScreen
import com.side.dnd.feature.price_rank.component.region.RegionRankScreen
import com.side.dnd.feature.price_rank.model.NationWideUiState
import side.dnd.design.component.CustomNavigationTab
import side.dnd.design.theme.EodigoColor

@Composable
internal fun PriceRankRoute(
    viewModel: PriceRankViewModel = hiltViewModel<PriceRankViewModel>(),
) {
    val context = LocalContext.current
    val uiState by viewModel.rankUiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(EodigoColor.White)
            .systemBarsPadding()
    ) {
        PriceRankScreen(viewModel = viewModel)
    }
}

@Composable
fun PriceRankLoading() {

}

@Composable
fun PriceRankScreen(
    viewModel: PriceRankViewModel = hiltViewModel<PriceRankViewModel>()
) {
    val uiState by viewModel.rankUiState.collectAsStateWithLifecycle()
    val tabs = listOf("전국팔도", "지역별")

    val pagerState = rememberPagerState(
        pageCount = { tabs.size }
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(EodigoColor.White)
            .systemBarsPadding()
    ) {
        CustomNavigationTab(
            tabs = listOf("전국팔도", "지역별"),
            content = { page ->
                when (page) {
                    0 -> { NationwideScreen(uiState = uiState) }
                    1 -> { RegionRankScreen() }
                }
            },
            pagerState = pagerState,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        )
    }
}

