package wiki.scene.lib_network.exception

import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.text.ParseException
import javax.net.ssl.SSLHandshakeException

object NetException {
    private const val UNAUTHORIZED = 401
    private const val FORBIDDEN = 403
    private const val NOT_FOUND = 404
    private const val REQUEST_TIMEOUT = 408
    private const val INTERNAL_SERVER_ERROR = 500
    private const val BAD_GATEWAY = 502
    private const val SERVICE_UNAVAILABLE = 503
    private const val GATEWAY_TIMEOUT = 504
    fun handleException(e: Throwable): ResponseException {
        //转换成ResponseException,根据状态码判定错误信息
        val ex: ResponseException
        return if (e is HttpException) {
            /**
             * 传入状态码，根据状态码判定错误信息
             */
            ex = ResponseException(e, ERROR.HTTP_ERROR)
            when (e.code()) {
                UNAUTHORIZED -> ex.message = "未验证"
                FORBIDDEN -> ex.message = "服务禁止访问"
                NOT_FOUND -> ex.message = "服务不存在"
                REQUEST_TIMEOUT -> ex.message = "请求超时"
                GATEWAY_TIMEOUT -> ex.message = "网关超时"
                INTERNAL_SERVER_ERROR -> ex.message = "服务器内部错误"
                BAD_GATEWAY -> ex.message = "请求失败"
                SERVICE_UNAVAILABLE -> ex.message = "请求失败"
                else -> ex.message = "请求失败"
            }
            ex
        } else if (e is JsonParseException
            || e is JSONException
            || e is ParseException
        ) {
            ex = ResponseException(e, ERROR.PARSE_ERROR)
            ex.message = "数据解析异常"
            ex
        } else if (e is ConnectException) {
            ex = ResponseException(e, ERROR.NETWORK_ERROR)
            ex.message = "请求失败"
            ex
        } else if (e is SSLHandshakeException) {
            ex = ResponseException(e, ERROR.SSL_ERROR)
            ex.message = "证书验证失败"
            ex
        } else if (e is DataNullException) {
            ex = ResponseException(e, ERROR.DATA_NULL)
            ex.message = "返回的数据为空"
            ex
        } else if (e is ApiException) {
            ex = ResponseException(e, ERROR.DATA_NULL)
            ex.message = e.msg
            ex
        } else if (e is UnAuthorizedException) {
            ex = ResponseException(e, ERROR.UNAUTHORIZED)
            ex.message = "请先登陆"
            ex
        } else {
            ex = ResponseException(e, ERROR.UNKNOWN)
            ex.message = "未知错误"
            ex
        }
    }

    /**
     * 约定异常
     */
    object ERROR {
        /**
         * 自定义异常
         */
        const val UNAUTHORIZED = 2001 //请求用户进行身份验证
        const val DATA_NULL = 2002      //数据为空


        /**
         * 未知错误
         */
        const val UNKNOWN = 1000

        /**
         * 解析错误
         */
        const val PARSE_ERROR = 1001

        /**
         * 网络错误
         */
        const val NETWORK_ERROR = 1002

        /**
         * 协议出错
         */
        const val HTTP_ERROR = 1003

        /**
         * 证书出错
         */
        const val SSL_ERROR = 1005
    }

    /**
     * 统一异常类，便于处理
     */
    class ResponseException(throwable: Throwable, var code: Int) : Exception(throwable) {
        override var message: String = ""
    }
}