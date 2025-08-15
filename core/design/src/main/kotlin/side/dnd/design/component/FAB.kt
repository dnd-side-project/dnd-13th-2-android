package side.dnd.design.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animate
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.MutatorMutex
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import side.dnd.design.R
import side.dnd.design.theme.SideprojectTheme
import side.dnd.design.utils.OffsetUtils.clampOffsetInCircle

enum class SelectableItem {
    NONE,
    LEFT,
    RIGHT;
}

@Composable
fun CircularFAB(
    modifier: Modifier = Modifier,
    componentSize: Dp = 77.dp,
    colors: ImmutableList<Color> = persistentListOf(Color(0xFFA87DFF), Color(0xFF4F28FF)),
    circularFabState: CircularFabState = rememberCircularFabState(size = componentSize),
    enabled: Boolean = true,
    content: @Composable BoxScope.() -> Unit = {},
) {
    var fabVisibility by remember { mutableStateOf(false) }
    val selectableItem by rememberUpdatedState(circularFabState.selectableItem)

    Box(modifier = modifier.size(161.dp)) {
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.TopCenter),
            visible = fabVisibility,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut(),
        ) {
            Canvas(
                Modifier
                    .size(161.dp)
            ) {
                drawArc(
                    color = Color.White,
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = true,
                    size = size
                )
            }

            Crossfade(targetState = selectableItem) { targetState ->
                when (targetState) {
                    SelectableItem.NONE -> {}
                    SelectableItem.LEFT -> {
                        Canvas(
                            Modifier
                                .size(161.dp)
                        ) {
                            val drawColor = Color(0xFFE0D9FE)
                            val strokePath = Path().apply {
                                val reducePx = 10f
                                val originalTop = 0f
                                val originalRect = Rect(
                                    Offset(x = 0f + reducePx, y = originalTop + reducePx),
                                    Size(
                                        width = size.width - reducePx * 2,
                                        height = size.height - reducePx * 2
                                    )
                                )

                                arcTo(
                                    rect = originalRect,
                                    startAngleDegrees = 180f,
                                    sweepAngleDegrees = 90f,
                                    forceMoveTo = true
                                )
                            }
                            val filledPath = Path().apply {
                                addPath(strokePath)
                                lineTo(
                                    x = center.x,
                                    y = size.height / 2
                                )
                                lineTo(x = 0f, y = size.height / 2)
                                close()
                            }

                            drawPath(strokePath, color = drawColor, style = Stroke(width = 2f))
                            drawPath(filledPath, color = drawColor)
                        }

                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.fa7_solid_won_sign),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(top = 26.dp, start = 34.dp)
                                .align(Alignment.TopStart)
                        )
                    }

                    SelectableItem.RIGHT -> {
                        Canvas(
                            Modifier
                                .size(161.dp)
                        ) {
                            val drawColor = Color(0xFFE0D9FE)
                            val strokePath = Path().apply {
                                val reducePx = 10f
                                val originalTop = 0f
                                val originalRect = Rect(
                                    Offset(x = 0f + reducePx, y = originalTop + reducePx),
                                    Size(
                                        width = size.width - reducePx * 2,
                                        height = size.height - reducePx * 2
                                    )
                                )

                                arcTo(
                                    rect = originalRect,
                                    startAngleDegrees = 270f,
                                    sweepAngleDegrees = 90f,
                                    forceMoveTo = true
                                )
                            }
                            val filledPath = Path().apply {
                                addPath(strokePath)
                                lineTo(
                                    x = center.x,
                                    y = size.height / 2
                                )
                                lineTo(x = center.x, y = size.height / 2)
                                close()
                            }

                            drawPath(strokePath, color = drawColor, style = Stroke(width = 2f))
                            drawPath(filledPath, color = drawColor)
                        }

                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.tdesign_location),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(top = 26.dp, end = 34.dp)
                                .align(Alignment.TopEnd)
                        )
                    }
                }
            }
        }

        content()

        Canvas(
            Modifier
                .padding(bottom = 42.dp)
                .size(componentSize)
                .align(Alignment.BottomCenter)
                .shadow(17.dp, shape = CircleShape)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            if (enabled)
                                fabVisibility = true
                        },
                        onDrag = { change, dragAmount ->
                            if (enabled)
                                circularFabState.drag(dragAmount)

                        },
                        onDragEnd = {
                            if (enabled) {
                                fabVisibility = false
                                circularFabState.animateOffset(Offset.Unspecified)
                            }
                        }
                    )
                }
        ) {
            val brush = Brush.linearGradient(
                colors = colors,
                start = Offset(center.x, 0f),
                end = Offset(center.x, size.height)
            )
            drawCircle(
                brush = brush,
                radius = componentSize.toPx() / 2.0f
            )
            translate(
                left = circularFabState.currentInteractionOffset.x,
                top = circularFabState.currentInteractionOffset.y
            ) {
                drawCircle(
                    color = Color.White,
                    radius = (componentSize / 7).toPx() / 2.0f,
                    center = Offset.Zero
                )
            }
        }
    }
}

@Composable
fun rememberCircularFabState(
    size: Dp,
    density: Density = LocalDensity.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): CircularFabState {
    return remember(size) {
        CircularFabState(
            maxSizePx = with(density) { size.toPx() },
            scope = coroutineScope,
        )
    }
}

@Stable
class CircularFabState(
    private val maxSizePx: Float,
    private val scope: CoroutineScope,
) {
    var currentInteractionOffset by mutableStateOf(center)
        private set

    val selectableItem by derivedStateOf {
        if (currentInteractionOffset.x < center.x)
            SelectableItem.LEFT
        else
            SelectableItem.RIGHT

    }

    private val mutatorMutex = MutatorMutex()

    val center: Offset get() = Offset(x = radius, y = radius)
    val radius: Float get() = maxSizePx / 2.0f

    fun drag(offset: Offset, threshold: Float = 0.7f) {
        val new = (currentInteractionOffset + (offset * threshold)).clampOffsetInCircle(
            center = center,
            radius = radius
        )

        currentInteractionOffset = new
    }

    /**
     * 주어진 targetOffset 으로 animate 처리하는 함수
     * 만약, targetOffset 이 Offset.Unspecified 이면, 중앙으로 이동
     *
     * @param targetOffset: 목표 지점
     */
    fun animateOffset(targetOffset: Offset) {
        scope.launch {
            mutatorMutex.mutate {
                animate<Offset, AnimationVector2D>(
                    typeConverter = TwoWayConverter(
                        convertToVector = { offset ->
                            AnimationVector2D(offset.x, offset.y)
                        },
                        convertFromVector = { vector ->
                            Offset(vector.v1, vector.v2)
                        }
                    ),
                    initialValue = currentInteractionOffset,
                    targetValue = if (targetOffset == Offset.Unspecified)
                        center
                    else
                        targetOffset
                ) { value, velocity ->
                    currentInteractionOffset = value.clampOffsetInCircle(
                        center = center,
                        radius = radius
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCircularFAB() = SideprojectTheme {
    Column(
        modifier = Modifier.size(300.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularFAB(
            modifier = Modifier.size(163.dp)
        )
    }
}