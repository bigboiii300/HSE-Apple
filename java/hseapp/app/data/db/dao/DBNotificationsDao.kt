package hseapp.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hseapp.app.data.db.entities.DBNotificationEntity

/**
 * обертка над SQL от гугла, тут смотреть не на что
 */
@Dao
interface DBNotificationsDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(newNotification: DBNotificationEntity)
    
    @Query("SELECT * FROM dbnotificationentity ORDER BY id")
    fun getAll(): List<DBNotificationEntity>
}