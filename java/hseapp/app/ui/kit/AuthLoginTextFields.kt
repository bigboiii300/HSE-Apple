package hseapp.app.ui.kit

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import hseapp.app.utils.compose.LocalHSEColors

/**
 * вьюшка поля ввода логина
 */
@Composable
fun AuthLoginTextFieldLogin(
    value: String,
    onValueChanged: (String) -> Unit,
    placeholder: String,
    focusRequester: FocusRequester,
    onNext: () -> Unit
) {
    val cornerRadius = 8.dp
    
    TextField(
        value = value,
        onValueChange = onValueChanged,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = LocalHSEColors.current.textFieldBackground,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = LocalHSEColors.current.primary,
            placeholderColor = LocalHSEColors.current.textBody,
            textColor = LocalHSEColors.current.textLabel
        ),
        shape = RoundedCornerShape(
            topStart = cornerRadius,
            topEnd = cornerRadius
        ),
        singleLine = true,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 48.dp)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = LocalHSEColors.current.textFieldStroke
                ),
                shape = RoundedCornerShape(
                    topStart = cornerRadius,
                    topEnd = cornerRadius
                )
            )
            .focusRequester(focusRequester),
        keyboardActions = KeyboardActions(
            onNext = { onNext() }
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        placeholder = {
            Text(text = placeholder)
        }
    )
}

/**
 * вьюшка поля ввода пароля
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AuthLoginTextFieldPassword(
    value: String,
    onValueChanged: (String) -> Unit,
    placeholder: String,
    focusRequester: FocusRequester,
    onDone: () -> Unit
) {
    val isPasswordShown = rememberSaveable { mutableStateOf(false) }
    val cornerRadius = 8.dp
    
    TextField(
        value = value,
        onValueChange = onValueChanged,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = LocalHSEColors.current.textFieldBackground,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = LocalHSEColors.current.primary,
            placeholderColor = LocalHSEColors.current.textBody,
            textColor = LocalHSEColors.current.textLabel
        ),
        shape = RoundedCornerShape(
            bottomStart = cornerRadius,
            bottomEnd = cornerRadius
        ),
        singleLine = true,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 48.dp)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = LocalHSEColors.current.textFieldStroke
                ),
                shape = RoundedCornerShape(
                    bottomStart = cornerRadius,
                    bottomEnd = cornerRadius
                )
            )
            .offset(y = (-1).dp)
            .focusRequester(focusRequester),
        keyboardActions = KeyboardActions(
            onDone = { onDone() }
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        placeholder = {
            Text(text = placeholder)
        },
        trailingIcon = {
            IconButton(onClick = { isPasswordShown.value = !isPasswordShown.value }) {
                AnimatedContent(
                    targetState = isPasswordShown.value,
                    transitionSpec = { scaleIn() with fadeOut() }
                ) {
                    Icon(
                        imageVector = if (it) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                        contentDescription = null,
                        tint = LocalHSEColors.current.primary
                    )
                }
            }
        },
        visualTransformation = if (isPasswordShown.value) VisualTransformation.None else PasswordVisualTransformation()
    )
}