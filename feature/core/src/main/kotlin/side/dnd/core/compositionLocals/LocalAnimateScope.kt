package side.dnd.core.compositionLocals

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.staticCompositionLocalOf

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedElementTransitionScope = staticCompositionLocalOf<SharedTransitionScope> { error("SharedTransitionScope not provided") }
val LocalAnimatedContentScope = staticCompositionLocalOf<AnimatedContentScope> { error("AnimatedVisibilityScope not provided") }
