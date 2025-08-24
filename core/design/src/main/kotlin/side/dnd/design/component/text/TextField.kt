package side.dnd.design.component.text

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import side.dnd.design.R
import side.dnd.design.component.HorizontalSpacer
import side.dnd.design.component.button.DefaultIconButton
import side.dnd.design.theme.EodigoTheme
import side.dnd.design.theme.LocalTypography
import side.dnd.design.utils.tu

@Composable
fun TextFieldWithActionBar(
    textFieldState: TextFieldState,
    modifier: Modifier = Modifier,
    textBackgroundColor: Color = Color.White,
    textBorderColor: Color = Color.White,
    textStyle: TextStyle = LocalTypography.current.body2Medium.copy(color = Color(0xFF817F84)),
    cursorBrush: Brush = SolidColor(Color(0xFF817F84)),
    @DrawableRes actionIcon: Int = R.drawable.ic_search,
    actionIconColor: Color = Color.White,
    actionIconBackgroundColor: Color = Color(0xFF2D2C2E),
    actionIconSize: Dp = 24.dp,
    hint: String = "텍스트를 입력해주세요.",
    enabled: Boolean = true,
    onClickEnabled: () -> Unit = {},
    onClickDisabled: () -> Unit = {},
    headerIcon: (@Composable () -> Unit)? = null,
) {
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DefaultTextField(
            modifier = Modifier
                .height(46.dp)
                .onFocusChanged {
                    if (!enabled && it.hasFocus) {
                        focusManager.clearFocus()
                        onClickDisabled()
                    }
                }
                .weight(1f),
            textFieldState = textFieldState,
            backgroundColor = textBackgroundColor,
            borderColor = textBorderColor,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            textStyle = textStyle,
            cursorBrush = cursorBrush,
            lineLimits = TextFieldLineLimits.SingleLine,
            hint = hint,
            headerIcon = headerIcon,
        )

        HorizontalSpacer(15.dp)

        DefaultIconButton(
            modifier = Modifier
                .size(46.dp)
                .shadow(12.dp, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp)),
            icon = actionIcon,
            onClick = {
                if (enabled) {
                    textFieldState.clearText()
                    focusManager.clearFocus()
                    onClickEnabled()
                } else
                    onClickDisabled()
            },
            iconSize = actionIconSize,
            iconTint = actionIconColor,
            backgroundTint = actionIconBackgroundColor,
            interactionSource = remember { MutableInteractionSource() },
        )
    }
}

@Composable
fun DefaultTextField(
    textFieldState: TextFieldState,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    textStyle: TextStyle = TextStyle.Default,
    cursorBrush: Brush = SolidColor(MaterialTheme.colorScheme.onSurface),
    outputTransformation: OutputTransformation? = null,
    inputTransformation: InputTransformation? = null,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.Default,
    headerIcon: (@Composable () -> Unit)? = null,
    hint: String = "텍스트를 입력해주세요.",
) {
    val focusManager = LocalFocusManager.current
    var isFocused by remember {
        mutableStateOf(false)
    }

    BasicTextField(
        modifier = modifier
            .shadow(12.dp, RoundedCornerShape(12.dp))
            .background(
                backgroundColor,
                RoundedCornerShape(12.dp),
            )
            .border(
                1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp),
            )
            .graphicsLayer {
                alpha = if (enabled) 1f else 0.3f
            }
            .onFocusChanged { focusState -> isFocused = focusState.isFocused },
        state = textFieldState,
        decorator = { innerTextField ->
            Row(
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                headerIcon?.let {
                    it()
                }
                HorizontalSpacer(width = 4.dp)
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp),
                ) {
                    if (textFieldState.text.isEmpty())
                        Text(
                            text = if (isFocused) "" else hint,
                            style = LocalTypography.current.body2Medium,
                            color = MaterialTheme.colorScheme.outlineVariant,
                            modifier = Modifier
                                .align(Alignment.CenterStart),
                        )

                    innerTextField()
                }
            }
        },
        enabled = enabled,
        readOnly = readOnly,
        keyboardOptions = keyboardOptions,
        onKeyboardAction = {
            focusManager.clearFocus()
        },
        textStyle = textStyle,
        cursorBrush = cursorBrush,
        outputTransformation = outputTransformation,
        inputTransformation = inputTransformation,
        lineLimits = lineLimits,
    )
}

@Composable
@Preview(showBackground = true, heightDp = 200)
private fun PreviewSearchTextField() = EodigoTheme {
    TextFieldWithActionBar(
        modifier = Modifier.fillMaxWidth(),
        textFieldState = rememberTextFieldState(),
    )
}

@Composable
@Preview(showBackground = true)
private fun PreviewDefaultTextField() = EodigoTheme {
    DefaultTextField(
        textFieldState = rememberTextFieldState(),
    )
}