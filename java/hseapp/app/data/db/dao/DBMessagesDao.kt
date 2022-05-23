package hseapp.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hseapp.app.data.db.entities.DBMessageEntity

/**
 * обертка над SQL от гугла, тут смотреть не на что
 */
@Dao
interface DBMessagesDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(newMessage: DBMessageEntity)
    
    @Query("SELECT * FROM DBMessageEntity WHERE chatId LIKE :chatId ORDER BY time")
    fun getForChat(chatId: Int): List<DBMessageEntity>
}