package wiki.scene.lib_db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import wiki.scene.lib_db.entity.SearchHistoryEntity

/**
 * Created by zlx on 2020/9/23 10:32
 * Email: 1170762202@qq.com
 * Description:
 */
@Dao
interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPerson(entity: SearchHistoryEntity): Long

    @Query("select * from SearchHistoryEntity")
    fun selectHis(): List<SearchHistoryEntity>

    @Query("delete from SearchHistoryEntity where id= :id")
    fun deleteById(id: Long)

    @Query("delete from SearchHistoryEntity")
    fun deleteAll()
}