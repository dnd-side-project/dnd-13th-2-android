package side.dnd.feature.home.home.component

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import side.dnd.design.R
import side.dnd.design.component.EodigoPaddingValues
import side.dnd.design.theme.EodigoTheme
import side.dnd.design.theme.LocalTypography
import side.dnd.design.utils.tu
import side.dnd.feature.home.state.Store
import side.dnd.feature.home.state.StoreType
import side.dnd.feature.home.store.SortType
import java.text.DecimalFormat

@Composable
fun MapMarker(
    store: Store,
    sortType: SortType,
    selected: Boolean,
    modifier: Modifier = Modifier,
    markerColor: Color = Color(0xFF7050FF),
    density: Density = LocalDensity.current,
) {
    val selectedTransition = updateTransition(selected)
    val animateColor by selectedTransition.animateColor {
        if (it)
            markerColor
        else
            Color.White
    }
    val animateTextColor by selectedTransition.animateColor {
        if (it)
            Color.White
        else
            markerColor
    }
    val animateScale by selectedTransition.animateFloat {
        if (it)
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
        text = when (sortType) {
            SortType.PRICE -> "${DecimalFormat("#,###").format(store.price)}원"
            SortType.DISTANCE -> "${store.distance}M"
        },
        style = textStyle
    )
    val image = ImageVector.vectorResource(
        when (sortType) {
            SortType.PRICE -> {
                when (store.type) {
                    StoreType.CAFE -> R.drawable.ic_coffee
                    StoreType.RESTAURANT -> R.drawable.ic_restaurant
                }
            }

            SortType.DISTANCE -> R.drawable.ic_location_selected
        }
    )
    val painter = rememberVectorPainter(image)
    val iconCircleSize = 29.dp
    val circlePadding = EodigoPaddingValues(vertical = 2.55.dp, horizontal = 3.23.dp)

    val animateWidthDp by selectedTransition.animateDp {
        if (it)
            iconCircleSize + circlePadding.calculateStartPadding()
        else
            0.dp
    }

    val textPadding = 6.dp
    val markerWidth = with(density) {
        textResult.size.width.toDp() + animateWidthDp + 6.dp + textPadding
    }

    Canvas(
        modifier = Modifier
            .width(markerWidth)
            .height(46.dp)
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
                val (left, top) = when (sortType) {
                    SortType.PRICE -> {
                        when (store.type) {
                            StoreType.CAFE -> LeftTopPadding(
                                left = 9.92.dp.toPx(),
                                top = 10.6.dp.toPx()
                            )

                            StoreType.RESTAURANT -> LeftTopPadding(
                                left = 9.59.dp.toPx(),
                                top = 10.63.dp.toPx()
                            )
                        }
                    }

                    SortType.DISTANCE -> LeftTopPadding(
                        left = 10.55.dp.toPx(),
                        top = 10.73.dp.toPx()
                    )
                }
                translate(left = left, top = top) {
                    draw(size = intrinsicSize)
                }
            }
        }
        translate(left = animateWidthDp.toPx()) {
            drawText(
                textResult,
                topLeft = Offset(
                    x = textPadding.toPx(),
                    y = 12.23.dp.toPx()
                ),
                color = animateTextColor,
            )
        }
    }
}

private data class LeftTopPadding(val left: Float, val top: Float)

@Composable
@Preview(showBackground = true)
private fun PreviewSelectedMapMarkerPrice() = EodigoTheme {
    Box(Modifier.size(150.dp)) {
        MapMarker(
            modifier = Modifier.align(Alignment.Center),
            store = Store.DEFAULT,
            selected = true,
            sortType = SortType.PRICE,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewSelectedMapMarkerDistance() = EodigoTheme {
    Box(Modifier.size(150.dp)) {
        MapMarker(
            modifier = Modifier.align(Alignment.Center),
            store = Store.DEFAULT,
            selected = true,
            sortType = SortType.DISTANCE,
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
            selected = false,
            sortType = SortType.PRICE,
        )
    }
}