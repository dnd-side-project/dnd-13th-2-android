package com.side.dnd.feature.price_rank

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import com.side.dnd.feature.price_rank.component.nationwide.NationwideScreen
import com.side.dnd.feature.price_rank.component.region.RegionRankScreen
import com.side.dnd.feature.price_rank.model.NationWideUiState
import com.side.dnd.feature.price_rank.navigation.PriceRankNavigationAction
import com.side.dnd.feature.price_rank.navigation.PriceRankRoute
import com.side.dnd.feature.price_rank.navigation.navigateToCategorySearch
import side.dnd.core.SnackBarMessage
import side.dnd.core.compositionLocals.LocalFABControl
import side.dnd.core.compositionLocals.LocalFabOnClickedListener
import side.dnd.core.compositionLocals.LocalNavigationActions
import side.dnd.core.compositionLocals.LocalShowSnackBar
import side.dnd.design.component.CategoryBottomSheet
import side.dnd.design.component.CustomNavigationTab
import side.dnd.design.component.FabType
import side.dnd.design.component.LocalCircularFabState
import side.dnd.design.component.button.clickableAvoidingDuplication
import side.dnd.design.component.effect.RememberEffect
import side.dnd.design.theme.EodigoColor

@Composable
fun PriceRankScreen(
    viewModel: PriceRankViewModel = hiltViewModel<PriceRankViewModel>(),
    productId: Int = 0,
    productName: String = ""
) {
    val uiState by viewModel.rankUiState.collectAsStateWithLifecycle()
    val tabs = listOf("전국팔도", "지역별")

    val pagerState = rememberPagerState(
        pageCount = { tabs.size }
    )
    val fabOnClickListener = LocalFabOnClickedListener.current
    var isFabClicked by remember {
        mutableStateOf(false)
    }
    val navigationAction = LocalNavigationActions.current
    val fabState = LocalCircularFabState.current
    val fabControl = LocalFABControl.current
    val showSnackBar = LocalShowSnackBar.current

    LaunchedEffect(productId, productName) {
        if (productId > 0 && productName.isNotEmpty()) {
            viewModel.loadProductData(productId, productName)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.tabChangeFlow.collect { tabIndex ->
            pagerState.animateScrollToPage(tabIndex)
        }
    }

    RememberEffect(Unit) {
        fabOnClickListener {
            isFabClicked = !isFabClicked
        }
        fabControl(false)
        fabState.animateOffset(fabState.center)
    }

    RememberEffect(isFabClicked) {
        if(isFabClicked)
            fabState.updateFabType(FabType.Multiply)
        else
            fabState.updateFabType(FabType.Plus)

    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(EodigoColor.White)
                .systemBarsPadding()
        ) {
            CustomNavigationTab(
                tabs = tabs,
                content = { page ->
                    when (page) {
                        0 -> { NationwideScreen(uiState = uiState, viewModel = viewModel) }
                        1 -> { RegionRankScreen(uiState = uiState) }
                    }
                },
                pagerState = pagerState,
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            )
        }

        if (isFabClicked) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f))
                    .clickableAvoidingDuplication {
                        isFabClicked = false
                    }
            )
        }

        CategoryBottomSheet(
            visible = isFabClicked,
            onCategoryClick = { categoryName ->
                isFabClicked = false
                when (categoryName) {
                    "찾아보기" -> {
                        navigationAction(PriceRankNavigationAction.NavigateToCategorySearch)
                    }
                    else -> {
                        showSnackBar.invoke(
                            SnackBarMessage(
                                headerMessage = "서비스 준비중인 기능이에요.",
                                contentMessage = "조금만 기다려 주세요."
                            )
                        )
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 120.dp)
        )
    }
}

