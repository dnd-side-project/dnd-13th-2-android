package side.dnd.feature.home.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.persistentListOf
import side.dnd.core.compositionLocals.LocalAnimatedContentScope
import side.dnd.core.compositionLocals.LocalNavigationActions
import side.dnd.core.compositionLocals.LocalSharedElementTransitionScope

internal class HomeUiStatePreviewParameter: PreviewParameterProvider<HomeUiState> {
    override val values: Sequence<HomeUiState> = sequenceOf(
        HomeUiState(
            searchWord = "검색어",
            stores = persistentListOf(),
            mapControl = MapControl.Empty,
            showSearchContent = false
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