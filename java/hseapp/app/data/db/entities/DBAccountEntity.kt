package hseapp.app.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * просто моделька аккаунта для базы данных, есть нормальная
 *
 * @see hseapp.app.data.objects.Account
 */
@Entity
data class DBAccountEntity(
    @PrimaryKey
    val id: Int,
    val fullName: String,
    val photo: String,
    val type: Int,
    val email: String
)

const val ACCOUNT_TYPE_PROFESSOR = 0
const val ACCOUNT_TYPE_ASSISTANT = 1
const val ACCOUNT_TYPE_STUDENT = 2