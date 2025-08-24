package side.dnd.feature.home.store.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import side.dnd.design.component.HorizontalWeightSpacer
import side.dnd.design.component.VerticalSpacer
import side.dnd.design.component.button.clickableAvoidingDuplication
import side.dnd.design.theme.EodigoTheme
import side.dnd.design.theme.LocalTypography
import side.dnd.design.utils.fillBounds
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun StoreBottomSheet(
    visibility: Boolean = false,
    sheetState: SheetState,
    selectedItem: String,
    selectItem: (String) -> Unit,
    updateSheetVisibility: (Boolean) -> Unit,
) {
    if (visibility)
        ModalBottomSheet(
            onDismissRequest = { updateSheetVisibility(false) },
            sheetState = sheetState,
            containerColor = Color.White,
        ) {
            StoreBottomSheetContent(
                selectedItem = selectedItem,
                selectItem = selectItem,
            )
        }
}

@Composable
private fun StoreBottomSheetContent(
    selectedItem: String,
    selectItem: (String) -> Unit,
) {
    VerticalSpacer(32.dp)

    Text(
        text = "정렬 옵션",
        style = LocalTypography.current.body1Medium,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(),
    )

    VerticalSpacer(5.dp)

    Column(
        modifier = Modifier
            .padding(20.dp)
            .border(
                1.dp,
                color = Color(0xFFECECED),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        StoreBottomSheetContentItem(
            title = "거리순",
            isSelected = selectedItem == "거리순",
            selectItem = selectItem,
        )
        HorizontalDivider(
            color = Color(0xFFECECED)
        )
        StoreBottomSheetContentItem(
            title = "가격순",
            isSelected = selectedItem == "가격순",
            selectItem = selectItem,
        )
    }
}

@Composable
private fun StoreBottomSheetContentItem(
    title: String,
    isSelected: Boolean = false,
    selectItem: (String) -> Unit,
) {
    val animateState by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0f,
        animationSpec = tween(250),
        label = ""
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickableAvoidingDuplication {
                selectItem(title)
            }
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = LocalTypography.current.body2Medium,
            color = Color(0xFF2D2C2E),
        )
        HorizontalWeightSpacer(1f)
        Canvas(Modifier.size(24.dp)) {
            val width = 15.99.dp.toPx()
            val height = 12.01.dp.toPx()
            val pathData =
                "M13.48,0.52C13.718,0.232 14.058,0.049 14.429,0.008C14.8,-0.032 15.172,0.074 15.466,0.304C15.611,0.416 15.731,0.555 15.82,0.714C15.91,0.874 15.967,1.049 15.987,1.23C16.008,1.412 15.991,1.596 15.94,1.771C15.888,1.946 15.802,2.109 15.686,2.25L8.166,11.49C8.04,11.643 7.884,11.767 7.708,11.857C7.531,11.946 7.338,11.998 7.14,12.008C6.943,12.019 6.745,11.988 6.56,11.919C6.375,11.849 6.206,11.742 6.064,11.604L0.424,6.064L0.326,5.96C-0.126,5.416 -0.094,4.614 0.424,4.106C0.942,3.598 1.76,3.566 2.314,4.01L2.42,4.106L6.96,8.546L13.5,0.526L13.48,0.52Z"
            val path = PathParser().parsePathString(pathData).toPath().apply {
                fillBounds(
                    strokeWidthPx = 1f,
                    maxWidth = width.roundToInt(),
                    maxHeight = height.roundToInt(),
                )
                translate(Offset(x = 4.dp.toPx(), y = 6.dp.toPx()))

            }

            clipRect(
                left = 0f,
                right = (width + 4.dp.toPx()) * animateState,
            ) {
                drawPath(path, color = Color(0xFF615F63), style = Fill)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PreviewStoreBottomSheetContent() = EodigoTheme {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(Color.White)
    ) {
        StoreBottomSheetContent(
            selectedItem = "거리순",
            selectItem = {},
        )
    }
}