package side.dnd.feature.home.store

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import side.dnd.core.compositionLocals.LocalAnimatedContentScope
import side.dnd.core.compositionLocals.LocalNavigationActions
import side.dnd.core.compositionLocals.LocalSharedElementTransitionScope
import side.dnd.design.R
import side.dnd.design.component.HorizontalSpacer
import side.dnd.design.component.VerticalSpacer
import side.dnd.design.component.button.clickableAvoidingDuplication
import side.dnd.design.component.effect.RememberEffect
import side.dnd.design.component.text.TextFieldWithActionBar
import side.dnd.design.theme.EodigoTheme
import side.dnd.design.theme.LocalTypography
import side.dnd.feature.home.home.PreviewHomeScope
import side.dnd.feature.home.store.component.StoreBottomSheet
import side.dnd.feature.home.store.component.StoreList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun StoreScreen(
    viewModel: StoreViewModel = hiltViewModel<StoreViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navigationAction = LocalNavigationActions.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.receiveAsFlow().collectLatest { effect ->
            when (effect) {
                is StoreSideEffect.Navigation -> {
                    navigationAction(effect.action)
                }
            }
        }
    }

    StoreScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun StoreScreen(
    uiState: StoreUiState,
    onEvent: (StoreEvent) -> Unit,
) {
    val textFieldState = rememberTextFieldState(initialText = uiState.searchWord)
    var bottomSheetVisibility by remember {
        mutableStateOf(false)
    }
    val bottomSheetState = rememberModalBottomSheetState()

    RememberEffect(uiState.searchWord) {
        textFieldState.edit {
            replace(0, length, uiState.searchWord)
        }
    }

    StoreBottomSheet(
        visibility = bottomSheetVisibility,
        sheetState = bottomSheetState,
        selectedItem = uiState.selectedSortType.display,
        selectItem = { item -> onEvent(StoreEvent.SelectSortType(SortType.getSortTypeByDisplay(item))) },
        updateSheetVisibility = { visibility -> bottomSheetVisibility = visibility }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
    ) {
        with(LocalSharedElementTransitionScope.current) {
            TextFieldWithActionBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 78.dp)
                    .sharedElement(
                        sharedContentState = rememberSharedContentState("Search"),
                        animatedVisibilityScope = LocalAnimatedContentScope.current,
                    ),
                textFieldState = textFieldState,
                hint = "오늘의 메뉴는?",
                actionIconBackgroundColor = Color(0xFF2D2C2E),
                actionIcon = R.drawable.ic_exit,
                actionIconSize = 14.dp,
                enabled = false,
                onClickDisabled = {
                    onEvent(StoreEvent.OnSearch)
                },
                headerIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left),
                        contentDescription = "Pop BackStack",
                        modifier = Modifier.clickableAvoidingDuplication {
                            onEvent(StoreEvent.PopBackStack)
                        }
                    )
                },
            )
        }

        VerticalSpacer(28.dp)

        Row(
            modifier = Modifier
                .width(98.dp)
                .height(36.dp)
                .background(Color(0xFFE0D9FE), RoundedCornerShape(16.dp))
                .clickableAvoidingDuplication {
                    bottomSheetVisibility = true
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = uiState.selectedSortType.display,
                style = LocalTypography.current.body2Medium,
                color = Color(0xFF362F58)
            )
            HorizontalSpacer(8.dp)
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_under),
                contentDescription = "Sort by price",
                modifier = Modifier
                    .size(24.dp)
                    .wrapContentSize(),
            )
        }

        VerticalSpacer(24.dp)

        StoreList(
            stores = uiState.stores,
            sortType = uiState.selectedSortType,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PreviewStoreScreen(
    @PreviewParameter(StoreUiStatePreviewParameter::class)
    uiState: StoreUiState,
) = EodigoTheme {
    PreviewHomeScope {
        StoreScreen(
            uiState = uiState,
            onEvent = {}
        )
    }
}