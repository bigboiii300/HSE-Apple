package hseapp.app.data.objects

/**
 * моделька пользователя
 */
data class User(
    val id: Int, //уникальный идентификатор пользователя
    val firstName: String, //имя
    val lastName: String, //фамилия
    val patronymic: String, //отчество
    val photoUrl: String, //URL адрес фотографии
    val role: Role //Тип пользователя (студент/преподаватель/ассистент)
)

enum class Role { STUDENT, PROFESSOR, ASSISTANT }
