package hseapp.app.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Просто моделька уведомления для базы данных, есть нормальная
 *
 * @see hseapp.app.data.objects.Notification
 */
@Entity
data class DBNotificationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: Int,
    val info: String,
    val date: Long? = null,
    //user
    val userId: Int,
    val fullName: String,
    val photo: String
)

const val NOTIFICATION_TYPE_MESSAGE = 0
const val NOTIFICATION_TYPE_DEADLINE = 1
const val NOTIFICATION_TYPE_ADDING_TO_COURSE = 2