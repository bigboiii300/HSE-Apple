package hseapp.app.data.source.core

import hseapp.app.data.objects.Account
import hseapp.app.data.objects.AuthError

/**
 * скелет датасорса авторизации
 *
 * @see hseapp.app.data.source.impl.FakeAuthDataSource
 */
abstract class AuthDataSource {
    
    abstract suspend fun auth(
        email: String,
        password: String,
        onSuccess: (Account) -> Unit,
        onError: (AuthError) -> Unit
    )
}