package hseapp.app.data.source.impl

import hseapp.app.data.db.HSEDatabase
import hseapp.app.data.objects.Account
import hseapp.app.data.objects.AuthError
import hseapp.app.data.objects.Role
import hseapp.app.data.objects.User
import hseapp.app.data.source.core.AuthDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.random.Random

/**
 * фейковая реализация датасорса авторизации
 */
class FakeAuthDataSource : AuthDataSource(), KoinComponent {

    /**
     * инжектим базу данных
     *
     * @see hseapp.app.data.di.trashModule
     */
    private val database: HSEDatabase by inject()

    /**
     * авторизуемся
     */
    override suspend fun auth(
        email: String,
        password: String,
        onSuccess: (Account) -> Unit,
        onError: (AuthError) -> Unit
    ) {
        delay(Random.nextLong(200, 1000)) //рандомная задержка для имитации загрузки
        val id = accounts["$email\\$password"] ?: -1488
        val account = database.getAccountDao().searchById(id)
        
        withContext(Dispatchers.Main) {
            if (account == null) onError(AuthError.InvalidData)
            else {
                onSuccess(
                    Account(
                        id = id,
                        email = email,
                        accessToken = "access_token",
                        refreshToken = "refresh_token",
                        expiresAt = System.currentTimeMillis() + 3_600_000L,
                        user = User(
                            id = id,
                            firstName = account.fullName.split(' ')[0],
                            lastName = account.fullName.split(' ')[1],
                            patronymic = account.fullName.split(' ')[2],
                            photoUrl = account.photo,
                            role = Role.STUDENT
                        )
                    )
                )
            }
        }
    }

    /**
     * аккаунты приложения, хранятся в виде mapOf("ЛОГИН\\ПАРОЛЬ" to ИДЕНТИФИКАТОР)
     */
    private val accounts = mapOf("ikiryakov@edu.hse.ru\\p4ssw0rd" to 1)
}