package wiki.scene.lib_network.util

import android.net.ParseException
import android.os.NetworkOnMainThreadException
import android.util.Log
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.net.UnknownServiceException

/**
 * @date: 2019\3\22 0022
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
/**
 * 异常处理
 */
object RxExceptionUtil {
    fun exceptionHandler(t: Throwable): String {
        var msg = "未知错误"
        if (t is UnknownHostException) {
            msg = "网络不可用"
        } else if (t is SocketTimeoutException) {
            msg = "请求网络超时"
        } else if (t is HttpException) {
            msg = convertStatusCode(t)
        } else if (t is JsonParseException || t is ParseException || t is JSONException) {
            msg = "数据解析错误"
        } else if (t is IllegalArgumentException) {
            Log.e("错误", t.message!!)
            msg = t.message!!
        } else if (t is UnknownServiceException) {
            msg = t.message!!
        } else if (t is NetworkOnMainThreadException) {
            msg = "网络请求在主线程"
        }
        return msg
    }

    private fun convertStatusCode(httpException: HttpException): String {
        return when {
            httpException.code() == 500 -> {
                "服务器发生错误"
            }
            httpException.code() == 404 -> {
                "请求地址不存在"
            }
            httpException.code() == 403 -> {
                "请求被服务器拒绝"
            }
            httpException.code() == 307 -> {
                "请求被重定向到其他页面"
            }
            else -> {
                httpException.message()
            }
        }
    }
}