package wiki.scene.lib_base.mmkv

import android.text.TextUtils
import com.blankj.utilcode.util.GsonUtils
import com.tencent.mmkv.MMKV
import wiki.scene.lib_base.base_api.res_data.UserInfo
import java.io.Serializable
import java.util.*

/**
 * FileName: MMkvHelper
 * Created by zlx on 2020/9/21 14:40
 * Email: 1170762202@qq.com
 * Description:
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
        //静态内部类
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