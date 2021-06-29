package wiki.scene.lib_db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import wiki.scene.lib_db.entity.SearchHistoryEntity

/**
 *
 * @Description:    搜索历史
 * @Author:         scene
 * @CreateDate:     2021/6/29 15:09
 * @UpdateUser:
 * @UpdateDate:     2021/6/29 15:09
 * @UpdateRemark:
 * @Version:        1.0.0
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