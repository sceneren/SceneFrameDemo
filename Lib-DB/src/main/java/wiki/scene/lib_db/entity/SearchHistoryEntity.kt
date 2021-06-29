package wiki.scene.lib_db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

/**
 *
 * @Description:    搜索历史
 * @Author:         scene
 * @CreateDate:     2021/6/29 15:23
 * @UpdateUser:
 * @UpdateDate:     2021/6/29 15:23
 * @UpdateRemark:
 * @Version:        1.0.0
 */
@Entity(indices = [Index(value = ["name"], unique = true)])
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val insertTime: Date = Date()
)