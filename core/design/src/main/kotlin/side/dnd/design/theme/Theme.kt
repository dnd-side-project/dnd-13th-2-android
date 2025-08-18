package side.dnd.design.theme

import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf


@Composable
fun EodigoTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalTypography provides Typography,
        LocalColors provides EodigoColor
    ) {
        ProvideTextStyle(Typography.body1Medium, content = content)
    }
}

@Immutable
object EodigoTheme {
    val typography: EodigoType
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val colors: EodigoColor
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current
}

val LocalTypography = staticCompositionLocalOf { Typography }
val LocalColors = staticCompositionLocalOf { EodigoColor }
