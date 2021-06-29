package wiki.scene.lib_db.manager

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import wiki.scene.lib_db.converter.DateConverter
import wiki.scene.lib_db.dao.SearchHistoryDao
import wiki.scene.lib_db.entity.SearchHistoryEntity

/**
 *
 * @Description:   搜索历史
 * @Author:         scene
 * @CreateDate:     2021/6/29 15:09
 * @UpdateUser:
 * @UpdateDate:     2021/6/29 15:09
 * @UpdateRemark:
 * @Version:        1.0.0
 */
@TypeConverters(value = [DateConverter::class])
@Database(entities = [SearchHistoryEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao?
}