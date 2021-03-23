package wiki.scene.lib_db.manager

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import wiki.scene.lib_db.converter.DateConverter
import wiki.scene.lib_db.dao.SearchHistoryDao
import wiki.scene.lib_db.entity.SearchHistoryEntity

/**
 * @date: 2019\9\4 0004
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
@TypeConverters(value = [DateConverter::class])
@Database(entities = [SearchHistoryEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao?
}