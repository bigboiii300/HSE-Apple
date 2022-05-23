package hseapp.app.data.objects

/**
 * Моделька аккаунта
 */
data class Account(
    val id: Int, //уникальный идентификатор
    val email: String, //email как ни странно
    val accessToken: String, //токен доступа к аккауну (живет недолго)
    val expiresAt: Long, //время прекращения работы токена доступа
    val refreshToken: String, //токен для обновления токена доступа (живет подольше)
    val user: User //данные об аккаунте
)