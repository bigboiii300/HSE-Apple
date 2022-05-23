package hseapp.app.data.objects

/**
 * Моделька курса
 */
data class Course(
    val id: Int, //уникальный идентификатор
    val name: String, //название
    val image: String, //ссылка на картинку
    val authorName: String, //имя автора
    val joined: Boolean //присоединился или нет
)
