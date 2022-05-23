package hseapp.app.data.objects

/**
 * моделька задания
 */
data class Task(
    val id: Int, //уникальный идентификатор
    val name: String, //название задания
    val image: String, //ссылка на картинку задания
    val deadline: Long, //дедлайн, в unix
    val author: String //имя автора задания
)
