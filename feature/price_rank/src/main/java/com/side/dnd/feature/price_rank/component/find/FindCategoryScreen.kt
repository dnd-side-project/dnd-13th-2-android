package com.side.dnd.feature.price_rank.component.find

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.side.dnd.feature.price_rank.R
import com.side.dnd.feature.price_rank.component.find.state.FindCategoryEvent
import com.side.dnd.feature.price_rank.component.find.state.FindCategorySideEffect
import com.side.dnd.feature.price_rank.component.find.state.FindCategoryUiState
import com.side.dnd.feature.price_rank.component.find.state.SearchCategory
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import side.dnd.core.SnackBarMessage
import side.dnd.core.compositionLocals.LocalNavigationActions
import side.dnd.core.compositionLocals.LocalShowSnackBar
import side.dnd.design.component.HorizontalWeightSpacer
import side.dnd.design.component.SquareCategoryItem
import side.dnd.design.component.VerticalSpacer
import side.dnd.design.component.button.clickableAvoidingDuplication
import side.dnd.design.theme.EodigoColor
import side.dnd.design.theme.EodigoTheme
import side.dnd.design.theme.LocalTypography

@Composable
internal fun FindCategoryScreen(
    viewModel: FindCategoryViewModel = hiltViewModel<FindCategoryViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navAction = LocalNavigationActions.current
    val showSnackBar = LocalShowSnackBar.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.receiveAsFlow().collectLatest { effect ->
            when (effect) {
                is FindCategorySideEffect.Navigation -> {
                    navAction.invoke(effect.navAction)
                }

                is FindCategorySideEffect.ShowSnackBar -> {
                    showSnackBar.invoke(effect.message)
                }
            }
        }
    }

    FindCategoryScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onCategoryClick = {
            showSnackBar.invoke(
                SnackBarMessage(
                    headerMessage = "서비스 준비중인 기능이에요.",
                    contentMessage = "조금만 기다려 주세요."
                )
            )
        },
        onDetailCategoryClick = {
            showSnackBar.invoke(
                SnackBarMessage(
                    headerMessage = "서비스 준비중인 기능이에요.",
                    contentMessage = "조금만 기다려 주세요."
                )
            )
        }
    )
}

@OptIn(FlowPreview::class)
@Composable
private fun FindCategoryScreen(
    uiState: FindCategoryUiState,
    onEvent: (FindCategoryEvent) -> Unit,
    onCategoryClick: (String) -> Unit = {},
    onDetailCategoryClick: (SearchCategory) -> Unit = {}
) {
    LaunchedEffect(uiState.searchQuery) {
        snapshotFlow {
            uiState.searchQuery.text
        }.debounce(250).distinctUntilChanged().collectLatest {
            onEvent(FindCategoryEvent.SearchProduct(it.toString()))
        }
    }

    val isSearching by remember(uiState.searchQuery.text) {
        derivedStateOf {
            uiState.searchQuery.text.isNotEmpty()
        }
    }

    var selectedItemIndex by remember {
        mutableIntStateOf(-1)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EodigoColor.White)
            .padding(horizontal = 20.dp),
    ) {
        VerticalSpacer(76.dp)

        SearchInputField(
            textFieldState = uiState.searchQuery,
            modifier = Modifier
                .padding(vertical = 24.dp)
                .background(EodigoColor.White),
            onClick = {
                if (uiState.searchQuery.text.isNotBlank()) {
                    onEvent(FindCategoryEvent.OnSearchClicked(uiState.searchQuery.text.toString()))
                }
            }
        )

        VerticalSpacer(32.dp)

        Crossfade(isSearching) { target ->
            if (!target)
                CategoryList(
                    onCategoryClick = { categoryName ->
                        onCategoryClick(categoryName)
                    },
                    onDetailCategoryClick = { category ->
                        onDetailCategoryClick(category)
                    },
                    modifier = Modifier
                        .background(EodigoColor.White)
                )
            else {
                SearchResultList(
                    uiState = uiState,
                    selectedItemIndex = selectedItemIndex,
                    onItemSelect = { idx ->
                        selectedItemIndex = idx
                    },
                    onEvent = onEvent,
                )
            }
        }
    }
}

@Composable
fun SearchResultList(
    uiState: FindCategoryUiState,
    selectedItemIndex: Int,
    modifier: Modifier = Modifier,
    onItemSelect: (Int) -> Unit,
    onEvent: (FindCategoryEvent) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        itemsIndexed(uiState.searchResult) { idx, result ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .drawBehind {
                        if (selectedItemIndex == idx)
                            drawRect(color = EodigoColor.Gray100)
                    }
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                onItemSelect(idx)
                            },
                            onTap = {
                                onEvent(
                                    FindCategoryEvent.SearchFiltering(
                                        id = result.id,
                                        name = result.name,
                                    )
                                )
                            }
                        )
                    }
                    .padding(
                        horizontal = 12.dp,
                        vertical = 16.dp
                    )
            ) {
                Text(
                    text = buildAnnotatedString {
                        val range = findMatchingTextRange(
                            str = result.name,
                            sub = uiState.searchQuery.text.toString(),
                        )

                        range?.let {
                            if (range.first > 0)
                                appendRange(result.name, 0, range.first)

                            withStyle(
                                style = SpanStyle(color = EodigoColor.Primary)
                            ) {
                                appendRange(result.name, it.first, it.last + 1)
                            }
                            if (range.last < result.name.length)
                                appendRange(result.name, range.last + 1, result.name.length)
                        } ?: append(result.name)
                    },
                    style = LocalTypography.current.body2Medium.copy(fontWeight = FontWeight.W400)
                )
            }
        }
    }
}

private fun findMatchingTextRange(str: String, sub: String): IntRange? {
    val start = str.indexOf(sub)
    if (start == -1) return null
    val end = start + sub.length - 1
    return start..end
}

@Composable
fun CategoryList(
    modifier: Modifier = Modifier,
    categories: ImmutableList<SearchCategory> = persistentListOf(
        SearchCategory("채소", R.drawable.ic_category_vegetable),
        SearchCategory("과일", R.drawable.ic_category_fruit),
        SearchCategory("곡물", R.drawable.ic_category_food_crops),
        SearchCategory("수산물", R.drawable.ic_category_seafood),
        SearchCategory("축산물", R.drawable.ic_category_livestock_products),
        SearchCategory("특용작물", R.drawable.ic_category_special_crop)
    ),
    onCategoryClick: (String) -> Unit,
    onDetailCategoryClick: (SearchCategory) -> Unit,
) {
    Column(modifier = modifier) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(40.dp),
            horizontalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            item(
                span = { GridItemSpan(4) }
            ) {
                Text(
                    text = "카테고리",
                    style = EodigoTheme.typography.body1Medium,
                    color = EodigoColor.Black,
                )
            }
            items(categories) { category ->
                SquareCategoryItem(
                    title = category.title,
                    icon = painterResource(category.icon),
                    onClick = { onDetailCategoryClick(category) }
                )
            }
        }
    }
}

@Composable
fun SearchInputField(
    textFieldState: TextFieldState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    BasicTextField(
        state = textFieldState,
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(12.dp),
                ambientColor = Color.Black.copy(alpha = 0.6f),
                spotColor = Color.Black.copy(alpha = 0.6f)
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            ),
        textStyle = LocalTypography.current.body3Medium.copy(
            color = if (textFieldState.text.isBlank()) EodigoColor.Gray500 else EodigoColor.Black
        ),
        decorator = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                ) {
                    if (textFieldState.text.isEmpty())
                        Text(
                            text = "자주 사는 물건, 어디가 더 저렴할까요?",
                            style = LocalTypography.current.body3Medium,
                            color = EodigoColor.Gray500,
                        )

                    innerTextField()
                }
                HorizontalWeightSpacer(1f)
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "검색",
                    modifier = Modifier
                        .size(24.dp)
                        .clickableAvoidingDuplication {
                            onClick()
                        },
                    tint = EodigoColor.Primary
                )
            }
        },
    )

}

@Preview(showBackground = true)
@Composable
fun SearchInputFieldPreview() {
    SearchInputField(
        textFieldState = TextFieldState(""),
        modifier = Modifier.padding(16.dp),
        onClick = {},
    )
}

@Preview(showBackground = true)
@Composable
fun FindCategoryScreenPreview(
    @PreviewParameter(FindCategoryUiStatePreviewParameter::class)
    uiState: FindCategoryUiState,
) {
    FindCategoryScreen(
        uiState = uiState,
        onEvent = {}
    )
}