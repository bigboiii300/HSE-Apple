package hseapp.app.ui.kit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import hseapp.app.utils.compose.LocalHSEColors

/**
 * вьюшка поля ввода, нигде не используется но вполне может в будущем, пускай пока будет
 */
@Composable
fun VKUITextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    
    TextField(
        value = value,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = LocalHSEColors.current.textFieldBackground,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = LocalHSEColors.current.primary,
            placeholderColor = LocalHSEColors.current.textBody,
            textColor = LocalHSEColors.current.textLabel
        ),
        shape = RoundedCornerShape(8.dp),
        singleLine = singleLine,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .defaultMinSize(
                minHeight = 48.dp,
                minWidth = 128.dp
            )
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = LocalHSEColors.current.textFieldStroke
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .then(modifier),
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        placeholder = placeholder,
        maxLines = maxLines,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource
    )
}