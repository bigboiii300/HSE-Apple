package hseapp.app.data.objects

/**
 * моделька сообщения
 */
data class Message(
    val id: Int, //уникальный идентификатор
    val time: Long, //время отправки в unix
    val author: User, //автор сообщения
    val reply: Message?, //сообщения на которое отвечает текущее
    val text: String //текст сообщения
)
