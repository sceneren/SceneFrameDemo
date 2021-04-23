package wiki.scene.lib_base.base_api.res_data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * FileName: UserInfo
 * Created by zlx on 2020/9/21 15:04
 * Email: 1170762202@qq.com
 * Description: 用户信息
 */
@Parcelize
data class UserInfo(
    val id: String = "",
    var name: String = "",
    var password: String = ""
) : Parcelable