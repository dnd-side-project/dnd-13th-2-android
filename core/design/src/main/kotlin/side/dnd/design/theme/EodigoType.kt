package side.dnd.design.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val FontStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
)

internal val Typography = EodigoType(
    tempFontR = FontStyle.copy(
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
    ),
    tempFontR50 = FontStyle.copy(
        fontSize = 45.sp,
        lineHeight = 52.sp,
        fontWeight = FontWeight.Medium,
      ),
)

@Immutable
data class  EodigoType(
    val tempFontR: TextStyle,
    val tempFontR50: TextStyle
)

