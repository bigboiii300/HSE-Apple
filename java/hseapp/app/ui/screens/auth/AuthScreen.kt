package hseapp.app.ui.screens.auth

import android.os.Handler
import android.os.Looper
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.os.postDelayed
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import hseapp.app.LocalActivity
import hseapp.app.R
import hseapp.app.data.objects.AuthError
import hseapp.app.ui.kit.VKUIFilledButton
import hseapp.app.ui.kit.VKUITextField
import hseapp.app.utils.compose.HSETypography
import hseapp.app.utils.compose.LocalHSEColors

/**
 * Экран авторизации
 */
@OptIn(ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class)
@Composable
@Destination(start = true)
fun AuthLoginScreen(viewModel: AuthViewModel = viewModel()) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val activity = LocalActivity.current

    var isLoading by rememberSaveable { mutableStateOf(false) }

    var login by rememberSaveable { mutableStateOf("") }
    val loginFocusRequester = remember { FocusRequester() }

    var code by rememberSaveable { mutableStateOf("") }
    val codeFocusRequester = remember { FocusRequester() }

    val notificationDuration = 3000L
    var errorText by rememberSaveable { mutableStateOf<String?>(null) }
    var errorVisible by rememberSaveable { mutableStateOf(false) }
    var errorShowedAt by rememberSaveable { mutableStateOf(-1L) }

    fun showNotification(message: String) {
        //определяем время показа уведомления
        val now = System.currentTimeMillis()
        errorShowedAt = now
        //настраиваем уведомление
        errorText = message
        //показываем
        errorVisible = true
        //по истечении времени скрываем
        Handler(Looper.getMainLooper()).postDelayed(notificationDuration) {
            if (errorShowedAt == now) { //убеждаемся, что скрываем именно текущее уведомление
                errorVisible = false
            }
        }
    }

    fun handleError(error: AuthError) {
        when (error) {
            AuthError.InvalidData -> {
                showNotification("Введены неверные данные")
            }
            AuthError.Unknown -> {
                showNotification("Что-то пошло не так")
            }
            //something later maybe
        }
    }

    fun auth() {
        //проверяем введена ли почта
        if (login.isBlank() && !viewModel.waitingForCode.value) {
            loginFocusRequester.requestFocus()
            showNotification("Введите почту")
            return
        }
        //проверяем введен ли код подтверждения
        if (code.isBlank() && viewModel.waitingForCode.value) {
            codeFocusRequester.requestFocus()
            showNotification("Введите код подтверждения")
            return
        }

        //скрываем клавиатуру и показываем индикатор загрузки
        keyboardController?.hide()
        isLoading = true

        //отправляем запрос авторизации
        if (viewModel.waitingForCode.value) {
            viewModel.auth(
                email = login,
                code = code,
                onSuccess = {
                    activity.recreate()
                },
                onError = {
                    isLoading = false
                    handleError(it)
                }
            )
        } else {
            viewModel.requestCode(
                email = login,
                onSuccess = {
                    isLoading = false
                    showNotification("Мы отправили Вам код подтверждения на почту")
                },
                onError = {
                    isLoading = false
                    handleError(it)
                }
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalHSEColors.current.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            //Логотип ВШЭ
            Image(
                painter = painterResource(id = R.drawable.ic_start_logo),
                contentDescription = stringResource(id = R.string.app_name),
                modifier = Modifier.size(96.dp),
                colorFilter = ColorFilter.tint(LocalHSEColors.current.primary)
            )
            //Отступ
            Spacer(modifier = Modifier.height(16.dp))
            AnimatedContent(targetState = viewModel.waitingForCode.value) {
                if (it) {
                    VKUITextField(
                        value = code,
                        onValueChange = { newValue -> code = newValue },
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(codeFocusRequester),
                        singleLine = true,
                        placeholder = { Text(text = "Код подтверждения") },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { auth() }
                        )
                    )
                    SideEffect {
                        codeFocusRequester.requestFocus()
                    }
                } else {
                    VKUITextField(
                        value = login,
                        onValueChange = { newValue -> login = newValue },
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(loginFocusRequester),
                        singleLine = true,
                        placeholder = { Text(text = "Email") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { auth() }
                        )
                    )
                    SideEffect {
                        loginFocusRequester.requestFocus()
                    }
                }
            }
//            //Поле ввода почты
//            AuthLoginTextFieldLogin(
//                value = login,
//                onValueChanged = { login = it },
//                placeholder = "Email",
//                focusRequester = loginFocusRequester,
//                onNext = {
//                    passwordFocusRequester.requestFocus()
//                }
//            )
//            //Поле ввода пароля
//            AuthLoginTextFieldPassword(
//                value = password,
//                onValueChanged = { password = it },
//                placeholder = "Пароль",
//                focusRequester = passwordFocusRequester,
//                onDone = ::auth
//            )
            //Отступ
            Spacer(modifier = Modifier.height(16.dp))
            //Кнопка входа
            VKUIFilledButton(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                isLoading = isLoading,
                onClick = ::auth
            ) {
                AnimatedContent(targetState = viewModel.waitingForCode.value) {
                    Text(
                        text = if (it) "Подтвердить" else "Отправить код",
                        style = HSETypography.body1.copy(
                            color = LocalHSEColors.current.onPrimary,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier
                            .alpha(
                                animateFloatAsState(if (!isLoading) 1f else 0f).value
                            )
                    )
                }
            }
        }

        //Уведомление
        AnimatedVisibility(
            visible = errorVisible,
            enter = slideInVertically(
                animationSpec = spring(
                    dampingRatio = 0.6f,
                    stiffness = Spring.StiffnessLow
                )
            ) { -it },
            exit = slideOutVertically { -it }
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 8.dp, vertical = 16.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = 4.dp,
                color = LocalHSEColors.current.background
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    //Иконка уведомления
                    Image(
                        painter = painterResource(id = R.drawable.ic_error_circle_outline),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(LocalHSEColors.current.primary),
                        modifier = Modifier.size(28.dp)
                    )
                    //Отступ
                    Spacer(modifier = Modifier.width(16.dp))
                    //Текст уведомления
                    Text(
                        text = errorText.toString(),
                        style = HSETypography.body1.copy(color = LocalHSEColors.current.textLabel)
                    )
                }
            }
        }

        BackHandler(enabled = viewModel.waitingForCode.value) {
            viewModel.back()
        }
    }
}