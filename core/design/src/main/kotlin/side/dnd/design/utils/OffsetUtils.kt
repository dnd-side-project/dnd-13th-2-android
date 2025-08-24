package side.dnd.design.utils

import androidx.compose.ui.geometry.Offset
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin
import kotlin.math.sqrt

object OffsetUtils {
    /**
     * Offset 에 대해 원의 중심과 반지름을 기반으로 가상의 원 공간 내에 위치시키도록 offset 을 보정하는 함수
     *
     * 원의 중심, 반지름을 기준으로 가상의 원 공간을 "유효 범위" 라 하고,
     * Offset Receiver 에 대해 "유효 범위" 내의 공간으로 보정함
     *
     * @param center: 원의 중심 offset
     * @param radius: 원의 반지름
     * @param threshold: 원의 크기 보정 계수, "유효 범위"를 줄이기 위한 보정 값
     */
    fun Offset.clampOffsetInCircle(
        center: Offset,
        radius: Float,
        threshold: Float = 1.0f,
    ): Offset {
        val dx = x - center.x
        val dy = y - center.y
        val distance = hypot(dx, dy)

        return if (distance <= (radius * threshold))
            this
        else {
            val angle = atan2(dy, dx)

            Offset(
                x = center.x + (radius * threshold) * cos(angle),
                y = center.y + (radius * threshold) * sin(angle)
            )
        }
    }
}