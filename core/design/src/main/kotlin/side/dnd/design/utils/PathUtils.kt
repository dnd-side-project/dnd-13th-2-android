package side.dnd.design.utils

import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import kotlin.math.min

fun Path.fillBounds(strokeWidthPx: Float, maxWidth: Int, maxHeight: Int) {
    val pathSize = getBounds()
    val matrix = Matrix()

    val horizontalOffset = pathSize.left - strokeWidthPx / 2
    val verticalOffset = pathSize.top - strokeWidthPx / 2
    val scaleWidth = maxWidth / (pathSize.width + strokeWidthPx)
    val scaleHeight = maxHeight / (pathSize.height + strokeWidthPx)
    val scale = min(scaleHeight, scaleWidth)

    matrix.scale(scale, scale)
    matrix.translate(-horizontalOffset, -verticalOffset)

    transform(matrix)
}