package hseapp.app.data.api

import hseapp.app.data.objects.AuthError
import hseapp.app.data.preferences.Prefs
import hseapp.app.data.retrofit.AuthService
import hseapp.app.data.serialization.auth.AccountResponse
import hseapp.app.data.serialization.auth.RequestEmailBody
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * утилита для легкой авторизации, тупо дергает датасорс
 */
object AuthHelper : KoinComponent {

    private val authService: AuthService by inject()

    /**
     * функция авторизации
     *
     * @param email почта
     * @param code код подтверждения
     * @param onSuccess коллбек успешной авторизации
     * @param onError коллбек ошибки авторизации (неправильные данные)
     */
    fun auth(
        email: String,
        code: String,
        onSuccess: () -> Unit,
        onError: (AuthError) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val body = Json.encodeToString(RequestEmailBody(email = email, code = code))
            runCatching {
                authService.auth(body).execute().let {
                    if (it.isSuccessful) {
                        val json = Json { ignoreUnknownKeys = true }
                        val accessToken = json.decodeFromString<AccountResponse>(it.body()!!).accessToken
                        Prefs.accessToken = accessToken
                        withContext(Dispatchers.Main) { onSuccess() }
                    } else onError(AuthError.Unknown)
                }
            }
        }
    }

    fun requestCode(
        email: String,
        onSuccess: () -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val body = Json.encodeToString(RequestEmailBody(email = email, code = ""))
            runCatching {
                authService.requestCode(body).execute().let {
                    if (it.isSuccessful) onSuccess()
                }
            }
        }
    }
}