package side.dnd.design.component.progress

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import side.dnd.design.theme.EodigoTheme
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ProgressIndicatorRotating(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        ProgressIndicatorInfiniteRotating()
    }
}

@Composable
fun ProgressIndicatorInfiniteRotating(
    modifier: Modifier = Modifier,
    counter: Int = 8,
    color: Color = MaterialTheme.colorScheme.onSurface,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite Transition")
    val rotationAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            tween(8 * 80, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation Animation"
    )

    Canvas(modifier = modifier) {

        val stroke = Stroke(
            width = 1.dp.toPx(),
        )

        val offset = ProgressIndicatorOffset(
            centerOffset = Offset(center.x, center.y),
            radius = 10.dp.toPx()
        )

        val path = offset.getProgressPath(
            counter = counter,
            endInclusive = counter
        )

        drawPath(path, color = color, style = stroke, alpha = 0.2f)

        val rotatePath = offset.getProgressPath(
            counter = counter,
            endInclusive = 1
        )

        for (i in 1..4) {
            rotate(degrees = (rotationAnimation.toInt() + i) * (360f / counter)) {
                drawPath(
                    rotatePath,
                    color = color,
                    style = stroke,
                    alpha = (0.2f + 0.2f * i).coerceIn(0f, 1f)
                )
            }
        }
    }
}

@Composable
fun ProgressIndicatorRotatingByParam(
    progress: Float,
    modifier: Modifier = Modifier,
    counter: Int = 8,
    color: Color = MaterialTheme.colorScheme.onSurface,
) {
    Canvas(modifier = modifier) {

        val stroke = Stroke(
            width = 1.dp.toPx(),
        )

        val offset = ProgressIndicatorOffset(
            centerOffset = Offset(center.x, center.y),
            radius = 10.dp.toPx()
        )

        val path = offset.getProgressPath(
            counter = counter,
            endInclusive = (progress * counter).toInt()
        )

        drawPath(path, color = color, style = stroke)
    }
}


internal class ProgressIndicatorOffset(
    private val centerOffset: Offset,
    private val radius: Float,
) {
    fun getProgressPath(counter: Int, endInclusive: Int) = Path().apply {
        for (i in 1..endInclusive) {
            val angle = 360.0 / counter * i

            moveTo(
                getPoint(ceta = angle).x,
                getPoint(ceta = angle).y
            )
            lineTo(
                getPoint(ceta = angle).x / 2f,
                getPoint(ceta = angle).y / 2f
            )
        }
    }

    fun getPoint(ceta: Double): Offset {
        return Offset(
            x = centerOffset.x + radius * cos(Math.toRadians(ceta - 90.0).toFloat()),
            y = centerOffset.y + radius * sin(Math.toRadians(ceta - 90.0).toFloat())
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewProgressIndicatorInfiniteRotating() = EodigoTheme {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp),
        contentAlignment = Alignment.Center
    ) {
        ProgressIndicatorInfiniteRotating()
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewProgressIndicatorRotatingWithTextByParam() = EodigoTheme {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp),
        contentAlignment = Alignment.Center
    ) {
        ProgressIndicatorRotatingByParam(
            progress = 0.5f,
        )
    }
}