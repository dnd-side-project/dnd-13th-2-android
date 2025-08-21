package side.dnd.feature.home.home.component

import android.R.attr.shadowColor
import android.graphics.BlurMaskFilter
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffset
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DropShadowScope
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import side.dnd.design.R
import side.dnd.design.component.EodigoPaddingValues
import side.dnd.design.component.text.tu
import side.dnd.design.theme.EodigoTheme
import side.dnd.design.theme.LocalTypography
import side.dnd.feature.home.home.Store
import side.dnd.feature.home.search.StoreType
import java.text.DecimalFormat
import androidx.core.graphics.toColorInt

@Composable
fun MapMarker(
    store: Store,
    selected: Boolean,
    modifier: Modifier = Modifier,
    markerColor: Color = Color(0xFF7050FF),
    density: Density = LocalDensity.current,
) {
    val selectedTransition = updateTransition(selected)
    val animateColor by selectedTransition.animateColor {
        if(it)
            markerColor
        else
            Color.White
    }
    val animateTextColor by selectedTransition.animateColor {
        if(it)
            Color.White
        else
            markerColor
    }
    val animateScale by selectedTransition.animateFloat {
        if(it)
            1f
        else
            0f
    }

    val textMeasurer = rememberTextMeasurer()
    val fontSize = 12.tu
    val textStyle = LocalTypography.current.body6Medium.copy(
        fontSize = fontSize,
        fontWeight = FontWeight.Bold,
        lineHeight = fontSize,
    )
    val textResult = textMeasurer.measure(
        text = "${DecimalFormat("#,###").format(store.price)}원",
        style = textStyle
    )
    val image = ImageVector.vectorResource(
        when (store.type) {
            StoreType.CAFE -> R.drawable.ic_coffee
            StoreType.RESTAURANT -> R.drawable.ic_coffee
        }
    )
    val painter = rememberVectorPainter(image)
    val iconCircleSize = 29.dp
    val circlePadding = EodigoPaddingValues(vertical = 2.55.dp, horizontal = 3.23.dp)

    val animateWidthDp by selectedTransition.animateDp {
        if(it)
            iconCircleSize + circlePadding.calculateHorizontalPadding()
        else
            0.dp
    }

    val markerWidth = with(density) {
        textResult.size.width.toDp() + animateWidthDp + 12.dp
    }

    Canvas(
        modifier = Modifier
            .width(markerWidth)
            .height(46.dp)
            .drawColoredShadow(Color.Black)
            .then(modifier)
    ) {
        val rect = size.toRect()
        val tapSize = Size(width = 24.dp.toPx(), height = 12.dp.toPx())
        val tapTopY = size.height - tapSize.height
        val tapPath = Path().apply {
            moveTo(rect.center.x - tapSize.width / 3, tapTopY)
            cubicTo(
                x1 = center.x - 1.5f,
                x2 = center.x + 1.5f,
                x3 = center.x + tapSize.width / 3,
                y1 = size.height,
                y2 = size.height,
                y3 = tapTopY
            )
            close()
        }

        drawRoundRect(
            color = animateColor,
            size = size.copy(height = 35.94.dp.toPx()),
            cornerRadius = CornerRadius(x = 100f, y = 100f)
        )
        drawPath(tapPath, color = animateColor, style = Fill)
        scale(scale = animateScale) {
            run {
                val circleRadius = iconCircleSize.toPx() / 2f

                drawCircle(
                    Color.White,
                    radius = circleRadius,
                    center = Offset(
                        x = circlePadding.calculateTopPadding().toPx() + circleRadius,
                        y = circlePadding.calculateStartPadding().toPx() + circleRadius
                    )
                )
            }
            with(painter) {
                translate(left = 9.92.dp.toPx(), top = 10.6.dp.toPx()) {
                    draw(size = intrinsicSize)
                }
            }
        }
        translate(left = animateWidthDp.toPx()) {
            drawText(
                textResult,
                topLeft = Offset(
                    x = 6.dp.toPx(),
                    y = 12.23.dp.toPx()
                ),
                color = animateTextColor,
            )
        }
    }
}

fun Modifier.drawColoredShadow(
    color: Color,
    alpha: Float = 0.2f,
    borderRadius: Dp = 0.dp,
    shadowRadius: Dp = 20.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = drawBehind {
    val transparentColor = color.copy(alpha = 0.0f).value.toLong().toColorInt()
    val shadowColor = color.copy(alpha = alpha).value.toLong().toColorInt()

    drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.apply {
            drawRoundRect(
                0f,
                0f,
                size.width,
                size.height,
                borderRadius.toPx(),
                borderRadius.toPx(),
                paint
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewSelectedMapMarker() = EodigoTheme {
    Box(Modifier.size(150.dp)) {
        MapMarker(
            modifier = Modifier.align(Alignment.Center),
            store = Store.DEFAULT,
            selected = true
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewUnselectedMapMarker() = EodigoTheme {
    Box(Modifier.size(150.dp)) {
        MapMarker(
            modifier = Modifier.align(Alignment.Center),
            store = Store.DEFAULT,
            selected = false
        )
    }
}