package side.dnd.design.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class EodigoPaddingValues(
    private val start: Dp = 0.dp,
    private val top: Dp = 0.dp,
    private val end: Dp = 0.dp,
    private val bottom: Dp = 0.dp
): PaddingValues {

    constructor(paddingValues: PaddingValues) : this(
        start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
        end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
        top = paddingValues.calculateTopPadding(),
        bottom = paddingValues.calculateBottomPadding(),
    )

    constructor(vertical: Dp = 0.dp, horizontal: Dp = 0.dp) : this (
        start = horizontal,
        end = horizontal,
        top = vertical,
        bottom = vertical,
    )

    override fun calculateLeftPadding(layoutDirection: LayoutDirection) = start

    override fun calculateTopPadding() = top

    override fun calculateRightPadding(layoutDirection: LayoutDirection) = end

    override fun calculateBottomPadding() = bottom

    fun calculateStartPadding() = start
    fun calculateEndPadding() = end
    fun calculateHorizontalPadding() = start + end
    fun calculateVerticalPadding() = top + bottom

    operator fun plus(other: PaddingValues): EodigoPaddingValues = EodigoPaddingValues(
        top = top + other.calculateTopPadding(),
        start = start + other.calculateStartPadding(LayoutDirection.Ltr),
        end = end + other.calculateEndPadding(LayoutDirection.Ltr),
        bottom = bottom + other.calculateBottomPadding(),
    )

    companion object {
        fun fromPaddingValues(paddingValues: PaddingValues): EodigoPaddingValues = EodigoPaddingValues(
            top = paddingValues.calculateTopPadding(),
            start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
            end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
            bottom = paddingValues.calculateBottomPadding(),
        )
    }
}