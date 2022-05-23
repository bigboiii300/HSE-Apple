package hseapp.app.ui.screens.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import hseapp.app.data.api.AuthHelper
import hseapp.app.data.objects.AuthError

/**
 * вьюмодель экрана авторизации
 */
class AuthViewModel : ViewModel() {

    val waitingForCode = mutableStateOf(false)
    
    fun auth(
        email: String,
        code: String,
        onSuccess: () -> Unit,
        onError: (AuthError) -> Unit
    ) {
        AuthHelper.auth(
            email = email,
            code = code,
            onSuccess = onSuccess,
            onError = onError
        )
    }

    fun requestCode(
        email: String,
        onSuccess: () -> Unit,
        onError: (AuthError) -> Unit
    ) {
        //проверяем, правильно ли введена почта
        // ВРЕМЕННО ВЫКЛЮЧИЛ
//        if (!isEmailCorrect(email)) {
//            onError(AuthError.InvalidData) //выдаем ошибку
//            return //завершаем выполнение функции
//        }

        AuthHelper.requestCode(email) {
            waitingForCode.value = true
            onSuccess()
        }
    }

    fun back() {
        waitingForCode.value = false
    }
    
    private fun isEmailCorrect(email: String): Boolean {
        val hseEmailPattern = "[a-zA-Z0-9]{1,256}(@edu\\.hse\\.ru|@hse\\.ru)".toPattern()
        return hseEmailPattern.matcher(email).matches()
    }
}