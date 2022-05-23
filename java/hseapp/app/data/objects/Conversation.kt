package hseapp.app.data.objects

/**
 * Моделька беседы
 */
data class Conversation(
    val id: Int, //уникальный идентификатор
    val name: String, //название
    val photo: String, //ссылка на фото
    val unreadCount: Int, //количество непрочитанных
    val lastMessage: Message? //последнее сообщение для превью
)
