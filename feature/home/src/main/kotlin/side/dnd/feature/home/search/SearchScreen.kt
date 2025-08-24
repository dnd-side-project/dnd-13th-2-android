package side.dnd.feature.home.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import side.dnd.core.compositionLocals.CommonNavigationAction
import side.dnd.core.compositionLocals.LocalAnimatedContentScope
import side.dnd.core.compositionLocals.LocalFABControl
import side.dnd.core.compositionLocals.LocalNavigationActions
import side.dnd.core.compositionLocals.LocalSharedElementTransitionScope
import side.dnd.design.R
import side.dnd.design.component.HorizontalSpacer
import side.dnd.design.component.VerticalSpacer
import side.dnd.design.component.VerticalWeightSpacer
import side.dnd.design.component.button.TextButton
import side.dnd.design.component.button.clickableAvoidingDuplication
import side.dnd.design.component.text.TextFieldWithActionBar
import side.dnd.design.theme.EodigoTheme
import side.dnd.design.theme.LocalTypography
import side.dnd.design.utils.tu
import side.dnd.feature.home.HomeNavigationAction
import side.dnd.feature.home.home.PreviewHomeScope
import side.dnd.feature.home.state.StoreType
import side.dnd.feature.home.store.StoreEvent

@Composable
internal fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel<SearchViewModel>(),
) {
    val searchUiState by viewModel.searchUiState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState { searchUiState.pageCount }
    val localFABControl = LocalFABControl.current
    val navActions = LocalNavigationActions.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.receiveAsFlow().collectLatest { effect ->
            when (effect) {
                is SearchSideEffect.SwitchPage -> pagerState.animateScrollToPage(effect.page)
                is SearchSideEffect.Navigate -> {
                    navActions(effect.action)
                    localFABControl(true)
                }
            }
        }
    }

    SearchContent(
        searchUiState = searchUiState,
        pagerState = pagerState,
        onEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
internal fun SearchContent(
    searchUiState: SearchUiState,
    pagerState: PagerState,
    onEvent: (SearchEvent) -> Unit,
) {
    val textFieldState = rememberTextFieldState()

    val showBrowser by remember(searchUiState.selectedCategory) {
        derivedStateOf {
            searchUiState.selectedCategory.isNotBlank()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        with(LocalSharedElementTransitionScope.current) {
            TextFieldWithActionBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 78.dp, start = 24.dp, end = 24.dp)
                    .sharedElement(
                        sharedContentState = rememberSharedContentState("Search"),
                        animatedVisibilityScope = LocalAnimatedContentScope.current,
                    ),
                textFieldState = textFieldState,
                hint = "오늘의 메뉴는?",
                actionIconBackgroundColor = Color(0xFF9B86FC),
                onClickEnabled = {
                    onEvent(SearchEvent.onBrowse(textFieldState.text.toString()))
                },
                headerIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left),
                        contentDescription = "Pop BackStack",
                        modifier = Modifier.clickableAvoidingDuplication {
                            onEvent(SearchEvent.PopBackStack)
                        }
                    )
                },
            )
        }

        VerticalSpacer(28.dp)

        SecondaryTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.White,
            indicator = {
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(pagerState.currentPage, matchContentSize = false),
                    color = Color(0xFF9B86FC)
                )
            }
        ) {
            StoreType.entries.forEachIndexed { idx, item ->
                Tab(
                    selected = pagerState.currentPage == idx,
                    onClick = {
                        onEvent(SearchEvent.SwitchPage(item))
                    },
                    text = {
                        Text(
                            text = item.display,
                            style = LocalTypography.current.title3Medium
                        )
                    },
                    selectedContentColor = Color(0xFF4D4C4F),
                    unselectedContentColor = Color(0xFFD8D7D9)
                )
            }
        }

        VerticalSpacer(26.dp)

        HorizontalPager(
            pagerState,
        ) { page ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                FlowRow(
                    maxItemsInEachRow = 4,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    StoreType.getStoreTypeByOrdinal(page).category.forEachIndexed { idx, category ->
                        FilterChip(
                            selected = searchUiState.selectedCategory === category,
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = Color(0xFFF2F2F3),
                                labelColor = Color(0xFF67666A),
                                selectedContainerColor = Color(0xFF9B86FC),
                                selectedLabelColor = Color.White
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                true,
                                selected = searchUiState.selectedCategory === category,
                                borderColor = Color.Transparent,
                                selectedBorderColor = Color.Transparent
                            ),
                            onClick = {
                                onEvent(
                                    SearchEvent.OnSelectChip(category)
                                )
                            },
                            label = {
                                Text(
                                    text = category,
                                    style = LocalTypography.current.body2Medium
                                )
                            },
                        )
                    }
                }


                VerticalWeightSpacer(1f)

                AnimatedVisibility(showBrowser) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp, vertical = 46.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(
                            modifier = Modifier
                                .clickableAvoidingDuplication {
                                    onEvent(SearchEvent.ResetSelectedCategories)
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "초기화",
                                style = LocalTypography.current.title2Medium,
                                color = Color(0xFF848484)
                            )
                            HorizontalDivider(
                                modifier = Modifier.width(56.dp),
                                color = Color(0xFF848484),
                            )
                        }
                        HorizontalSpacer(30.dp)
                        TextButton(
                            text = "찾아보기",
                            onClick = {
                                onEvent(SearchEvent.onBrowse(searchUiState.selectedCategory))
                            },
                            modifier = Modifier
                                .height(48.dp)
                                .weight(1f),
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@Preview(showBackground = true)
private fun PreviewSearchScreen(
    @PreviewParameter(SearchUiStatePreviewParameter::class)
    uiState: SearchUiState
) = EodigoTheme {
    PreviewHomeScope {
        SearchContent(
            searchUiState = uiState,
            pagerState = rememberPagerState { 2 },
            onEvent = {},
        )
    }
}