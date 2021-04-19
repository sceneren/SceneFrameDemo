package wiki.scene.bean.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    var email: String = "",
    var id: String = "",
    var token: String = ""
) : Parcelable
