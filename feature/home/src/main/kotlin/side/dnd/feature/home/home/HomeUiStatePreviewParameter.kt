package side.dnd.feature.home.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.persistentListOf
import side.dnd.core.compositionLocals.LocalAnimatedContentScope
import side.dnd.core.compositionLocals.LocalNavigationActions
import side.dnd.core.compositionLocals.LocalSharedElementTransitionScope
import side.dnd.feature.home.state.Store
import side.dnd.feature.home.store.SortType

internal class HomeUiStatePreviewParameter : PreviewParameterProvider<HomeUiState> {
    override val values: Sequence<HomeUiState>
        get() = sequenceOf(
            HomeUiState.Empty,
            HomeUiState.Empty.copy(
                searchWord = TextFieldState("카페 테인"),
                stores = persistentListOf(
                    Store.DEFAULT,
                    Store.DEFAULT,
                    Store.DEFAULT,
                    Store.DEFAULT,
                    Store.DEFAULT,
                ),
                isLocationTracking = true,
                sortType = SortType.DISTANCE,
            ),
        )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun PreviewHomeScope(content: @Composable () -> Unit) {
    SharedTransitionScope {
        AnimatedContent(true, modifier = it) {
            CompositionLocalProvider(
                LocalAnimatedContentScope provides this@AnimatedContent,
                LocalSharedElementTransitionScope provides this@SharedTransitionScope,
                LocalNavigationActions provides {},
            ) {
                if (it)
                    content()
            }
        }
    }
}