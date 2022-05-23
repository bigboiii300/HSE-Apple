package hseapp.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hseapp.app.data.db.entities.DBAccountEntity

/**
 * обертка над SQL от гугла, тут смотреть не на что
 */
@Dao
interface DBAccountDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(account: DBAccountEntity)
    
    @Query("SELECT * FROM dbaccountentity WHERE id LIKE :id LIMIT 1")
    fun searchById(id: Int): DBAccountEntity?
}