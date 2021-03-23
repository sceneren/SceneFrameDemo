package wiki.scene.lib_db.manager

import android.content.Context
import android.text.TextUtils
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * @date: 2019\9\4 0004
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
class DbUtil {
    private var appDataBase: AppDataBase? = null
    private var context: Context? = null
    private var dbName: String? = null
    fun init(context: Context, dbName: String?) {
        this.context = context.applicationContext
        this.dbName = dbName
        appDataBase = null
    }

    fun getAppDataBase(): AppDataBase {
        if (appDataBase == null) {
            if (TextUtils.isEmpty(dbName)) {
                throw NullPointerException("dbName is null")
            }
            appDataBase = Room.databaseBuilder(context!!, AppDataBase::class.java, dbName!!)
                .allowMainThreadQueries()
                .enableMultiInstanceInvalidation() //                    .addMigrations(MIGRATION_1_2)
                .build()
        }
        return appDataBase!!
    }

    companion object {
        @JvmStatic
        var instance: DbUtil? = null
            get() {
                if (field == null) {
                    field = DbUtil()
                }
                return field
            }
            private set

        /**
         * 数据库版本 1->2 user表格新增了age列
         */
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `book` (`bookId` INTEGER PRIMARY KEY autoincrement, `bookName` TEXT , `user_id` INTEGER, 'time' INTEGER)"
                )
            }
        }

        /**
         * 数据库版本 2->3 新增book表格
         */
        val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `book` (`bookId` INTEGER PRIMARY KEY autoincrement, `bookName` TEXT , `user_id` INTEGER, 'time' INTEGER)"
                )
            }
        }
    }
}