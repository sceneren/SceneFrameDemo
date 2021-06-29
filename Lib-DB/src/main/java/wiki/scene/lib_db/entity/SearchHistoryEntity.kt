package wiki.scene.lib_db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by zlx on 2020/9/23 10:29
 * Email: 1170762202@qq.com
 * Description: 搜索历史
 */
@Entity(indices = [Index(value = ["name"],unique = true)])
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name:String,
    val insertTime:Date
)