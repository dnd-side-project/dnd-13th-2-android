package side.dnd.feature.home.search

import androidx.activity.compose.BackHandler
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
import side.dnd.core.compositionLocals.LocalAnimatedContentScope
import side.dnd.core.compositionLocals.LocalFABControl
import side.dnd.core.compositionLocals.LocalNavigationActions
import side.dnd.core.compositionLocals.LocalSharedElementTransitionScope
import side.dnd.design.component.HorizontalSpacer
import side.dnd.design.component.VerticalSpacer
import side.dnd.design.component.VerticalWeightSpacer
import side.dnd.design.component.button.TextButton
import side.dnd.design.component.button.clickableAvoidingDuplication
import side.dnd.design.component.text.TextFieldWithSearchBar
import side.dnd.design.component.text.tu
import side.dnd.design.theme.EodigoTheme
import side.dnd.feature.home.HomeNavigationAction
import side.dnd.feature.home.home.PreviewHomeScope

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
                    navActions(HomeNavigationAction.NavigateToHome)
                    localFABControl(true)
                }
            }
        }
    }

    BackHandler {
        navActions(HomeNavigationAction.NavigateToHome)
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

    val showBrowser by remember(searchUiState.categoryPointer) {
        derivedStateOf {
            searchUiState.categories.entries.find { it.key == searchUiState.categoryPointer } == null
        }
    }

    val selectedCategories by remember(
        searchUiState.selectedCategories,
        searchUiState.categoryPointer
    ) {
        derivedStateOf {
            searchUiState.categories.filter {
                it.key in searchUiState.selectedCategories.keys || it.key == searchUiState.categoryPointer
            }.entries
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        with(LocalSharedElementTransitionScope.current) {
            TextFieldWithSearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 78.dp, start = 24.dp, end = 24.dp)
                    .sharedElement(
                        sharedContentState = rememberSharedContentState("Search"),
                        animatedVisibilityScope = LocalAnimatedContentScope.current,
                    ),
                textFieldState = textFieldState,
                hint = "오늘의 메뉴는?",
                searchIconBackgroundColor = Color(0xFF9B86FC)
            )
        }

        VerticalSpacer(28.dp)

        SecondaryTabRow(
            selectedTabIndex = searchUiState.selectedTab.ordinal,
            containerColor = Color.White,
            indicator = @Composable {
                TabRowDefaults.SecondaryIndicator(
                    color = Color(0xFF9B86FC)
                )
            }
        ) {
            SearchTab.entries.forEachIndexed { idx, item ->
                Tab(
                    selected = searchUiState.selectedTab.ordinal == idx,
                    onClick = {
                        onEvent(SearchEvent.SwitchPage(item))
                    },
                    text = {
                        Text(
                            text = item.display,
                            style = TextStyle(
                                fontWeight = FontWeight.W400,
                                fontSize = 20.tu,
                                letterSpacing = (-0.05).em,
                                lineHeight = 24.tu,
                            )
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
                selectedCategories.forEachIndexed { mainCategoryIdx, mainCategory ->
                    FlowRow(
                        maxItemsInEachRow = 4,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        if (mainCategoryIdx != 0) {
                            HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp))
                        }

                        mainCategory.value.forEachIndexed { subCategoryIdx, subCategory ->
                            FilterChip(
                                selected = searchUiState.selectedCategories.containsValue(
                                    subCategory
                                ),
                                colors = FilterChipDefaults.filterChipColors(
                                    containerColor = Color(0xFFF2F2F3),
                                    labelColor = Color(0xFF67666A),
                                    selectedContainerColor = Color(0xFF9B86FC),
                                    selectedLabelColor = Color.White
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    true,
                                    selected = searchUiState.selectedCategories.containsValue(
                                        subCategory
                                    ),
                                    borderColor = Color.Transparent,
                                    selectedBorderColor = Color.Transparent
                                ),
                                onClick = {
                                    onEvent(
                                        SearchEvent.OnSelectChip(
                                            subCategoryIdx,
                                            mainCategory.key,
                                            subCategory
                                        )
                                    )
                                },
                                label = {
                                    Text(
                                        text = subCategory,
                                        fontSize = 16.tu,
                                        fontWeight = FontWeight.W400,
                                        letterSpacing = (-0.05).em,
                                        lineHeight = 24.tu,
                                    )
                                },
                            )
                        }
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
                                .width(60.dp)
                                .clickableAvoidingDuplication {
                                    onEvent(SearchEvent.ResetSelectedCategories)
                                }
                        ) {
                            Text(
                                text = "초기화",
                                style = TextStyle(
                                    fontSize = 20.tu,
                                    fontWeight = FontWeight.W400
                                ),
                                color = Color(0xFF848484)
                            )
                            HorizontalDivider(
                                color = Color(0xFF848484),
                            )
                        }
                        HorizontalSpacer(30.dp)
                        TextButton(
                            text = "찾아보기",
                            onClick = {
                                onEvent(SearchEvent.onBrowse)
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