package wiki.scene.lib_db.manager

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.io.Serializable

/**
 *
 * @Description:    DB工具类
 * @Author:         scene
 * @CreateDate:     2021/6/29 15:39
 * @UpdateUser:
 * @UpdateDate:     2021/6/29 15:39
 * @UpdateRemark:
 * @Version:        1.0.0
 */
class DbUtil private constructor() : Serializable {

    companion object {
        @JvmStatic
        fun getInstance(): DbUtil {//全局访问点
            return SingletonHolder.mInstance
        }

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

    private object SingletonHolder {
        @SuppressLint("StaticFieldLeak")
        val mInstance: DbUtil = DbUtil()
    }

    private fun readResolve(): Any {
        //防止单例对象在反序列化时重新生成对象
        return SingletonHolder.mInstance
    }


    private var appDataBase: AppDataBase? = null
    private var context: Context? = null
    private var dbName: String? = null

    fun init(context: Context, dbName: String) {
        this.context = context.applicationContext
        this.dbName = dbName
        appDataBase = null
    }

    fun getAppDataBase(): AppDataBase {
        if (appDataBase == null) {
            if (dbName.isNullOrEmpty()) {
                throw NullPointerException("dbName is null")
            }
            appDataBase = Room.databaseBuilder(context!!, AppDataBase::class.java, dbName!!)
                .allowMainThreadQueries()
                .enableMultiInstanceInvalidation()
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build()
        }
        return appDataBase!!
    }


}