package side.dnd.design.utils

import androidx.compose.ui.geometry.Offset
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

object OffsetUtils {
    fun Offset.coerceIn(min: Float = 0f, max: Float): Offset {
        return copy(
            x = x.coerceIn(min, max),
            y = y.coerceIn(min, max)
        )
    }

    fun Offset.coerceIn(offset: Offset): Offset {
        return copy(
            x = x.coerceIn(0f, offset.x),
            y = y.coerceIn(0f, offset.y),
        )
    }

    fun Offset.clampOffsetInCircle(center: Offset, radius: Float): Offset {
        val dx = x - center.x
        val dy = y - center.y
        val distance = hypot(dx, dy)

        return if (distance <= radius)
            this
        else {
            val angle = atan2(dy, dx)

            Offset(
                x = center.x + radius * cos(angle),
                y = center.y + radius * sin(angle)
            )
        }
    }
}