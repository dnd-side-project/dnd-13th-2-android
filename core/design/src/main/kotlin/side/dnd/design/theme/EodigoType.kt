package side.dnd.design.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import side.dnd.design.R

private val GMarketSansFontFamily = FontFamily(
    Font(R.font.gmarketsanslight, FontWeight.Light),
    Font(R.font.marketsansmedium, FontWeight.Medium),
    Font(R.font.gmarketsansbold, FontWeight.Bold)
)

private val FontStyle = TextStyle(
    fontFamily = GMarketSansFontFamily,
    fontWeight = FontWeight.Normal,
)

internal val Typography = EodigoType(
    title1Medium = FontStyle.copy(
        fontSize = 22.sp,
        lineHeight = 32.sp,
        fontWeight = FontWeight.Medium,
    ),
    title2Medium = FontStyle.copy(
        fontSize = 20.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Medium,
    ),
    title3Medium = FontStyle.copy(
        fontSize = 20.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Medium,
    ),

    body1Medium = FontStyle.copy(
        fontSize = 18.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Medium,
    ),
    body2Medium = FontStyle.copy(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Medium,
    ),
    body3Medium = FontStyle.copy(
        fontSize = 15.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Medium,
    ),
    body4Medium = FontStyle.copy(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Medium,
    ),
    body5Medium = FontStyle.copy(
        fontSize = 13.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Medium,
    ),
    body6Medium = FontStyle.copy(
        fontSize = 12.sp,
        lineHeight = 18.sp,
        fontWeight = FontWeight.Medium,
    ),

    body6Bold = FontStyle.copy(
        fontSize = 18.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Bold,
    ),

    )

@Immutable
data class  EodigoType(
    val title1Medium: TextStyle,
    val title2Medium: TextStyle,
    val title3Medium: TextStyle,
    val body1Medium: TextStyle,
    val body2Medium: TextStyle,
    val body3Medium: TextStyle,
    val body4Medium: TextStyle,
    val body5Medium: TextStyle,
    val body6Medium: TextStyle,
    val body6Bold: TextStyle,
)

