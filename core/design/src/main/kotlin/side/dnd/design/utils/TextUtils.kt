package side.dnd.design.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Stable
val Int.tu: TextUnit
    @Composable
    get() = with(LocalDensity.current) { (this@tu / fontScale).sp }

fun AnnotatedString.Builder.withColor(color: Color, block: AnnotatedString.Builder.() -> Unit) {
    withStyle(SpanStyle(color = color)) { block() }
}

