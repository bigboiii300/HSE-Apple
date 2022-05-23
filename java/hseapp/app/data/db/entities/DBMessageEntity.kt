package hseapp.app.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * просто моделька сообщения для базы данных, есть нормальная
 *
 * @see hseapp.app.data.objects.Message
 */
@Entity
data class DBMessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val chatId: Int,
    val time: Long,
    val authorId: Int,
    val authorFullName: String,
    val authorPhoto: String,
    val text: String,
    val replyMessageId: Int? = null,
    val replyMessageText: String? = null,
    val replyMessageAuthorFullName: String? = null
)
