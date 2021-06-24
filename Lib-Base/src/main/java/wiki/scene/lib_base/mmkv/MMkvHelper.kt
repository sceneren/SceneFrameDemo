package wiki.scene.lib_base.mmkv

import android.text.TextUtils
import com.blankj.utilcode.util.GsonUtils
import com.tencent.mmkv.MMKV
import wiki.scene.entity.UserInfo
import java.io.Serializable
import java.util.*

/**
 *
 * @Description:    MMkvHelper
 * @Author:         scene
 * @CreateDate:     2021/6/24 16:00
 * @UpdateUser:
 * @UpdateDate:     2021/6/24 16:00
 * @UpdateRemark:
 * @Version:        1.0.0
 */
class MMkvHelper private constructor() : Serializable {
    private val mmkv = MMKV.defaultMMKV()

    companion object {
        @JvmStatic
        fun getInstance(): MMkvHelper {
            return SingletonHolder.mInstance
        }
    }

    private object SingletonHolder {
        val mInstance: MMkvHelper = MMkvHelper()
    }

    private fun readResolve(): Any {//防止单例对象在反序列化时重新生成对象
        return SingletonHolder.mInstance
    }

    /**
     * 保存用户信息
     */
    fun saveUserInfo(userInfo: UserInfo?) {
        mmkv?.encode(MMKVKey.USER_INFO, userInfo)
    }

    val userInfo: UserInfo?
        get() = mmkv?.decodeParcelable(MMKVKey.USER_INFO, UserInfo::class.java)

    fun saveLanguage(locale: Locale?) {
        mmkv?.encode(MMKVKey.LANGUAGE, GsonUtils.toJson(locale))
    }

    val language: Locale?
        get() {
            val s = mmkv!!.decodeString(MMKVKey.LANGUAGE)
            return if (TextUtils.isEmpty(s)) {
                null
            } else try {
                GsonUtils.fromJson(s, Locale::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

    fun logout() {
        mmkv!!.remove(MMKVKey.USER_INFO)
    }
}