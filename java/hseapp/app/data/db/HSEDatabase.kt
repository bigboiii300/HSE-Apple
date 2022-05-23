package hseapp.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import hseapp.app.data.db.dao.DBAccountDao
import hseapp.app.data.db.dao.DBMessagesDao
import hseapp.app.data.db.dao.DBNotificationsDao
import hseapp.app.data.db.entities.DBAccountEntity
import hseapp.app.data.db.entities.DBMessageEntity
import hseapp.app.data.db.entities.DBNotificationEntity

/**
 * оберка над SQL, тут смотреть не на что
 */
@Database(
    version = 1,
    entities = [
        DBAccountEntity::class,
        DBNotificationEntity::class,
        DBMessageEntity::class
    ]
)
abstract class HSEDatabase : RoomDatabase() {

    abstract fun getAccountDao(): DBAccountDao

    abstract fun getNotificationsDao(): DBNotificationsDao

    abstract fun getMessagesDao(): DBMessagesDao
}