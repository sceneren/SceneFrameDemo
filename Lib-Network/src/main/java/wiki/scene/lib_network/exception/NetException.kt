package wiki.scene.lib_network.exception

import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.StringUtils
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import wiki.scene.lib_common.provider.router.RouterUtil
import wiki.scene.lib_network.R
import java.net.ConnectException
import java.net.UnknownHostException
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
        return if (NetworkUtils.isConnected()) {
            if (e is HttpException) {
                /**
                 * 传入状态码，根据状态码判定错误信息
                 */
                ex = ResponseException(e, ERROR.HTTP_ERROR)
                when (e.code()) {
                    UNAUTHORIZED -> ex.message =
                        StringUtils.getString(R.string.lib_network_error_401)
                    FORBIDDEN -> ex.message =
                        StringUtils.getString(R.string.lib_network_error_403)
                    NOT_FOUND -> ex.message = StringUtils.getString(R.string.lib_network_error_404)
                    REQUEST_TIMEOUT -> ex.message =
                        StringUtils.getString(R.string.lib_network_error_408)
                    GATEWAY_TIMEOUT -> ex.message =
                        StringUtils.getString(R.string.lib_network_error_504)
                    INTERNAL_SERVER_ERROR -> ex.message =
                        StringUtils.getString(R.string.lib_network_error_500)
                    BAD_GATEWAY -> ex.message =
                        StringUtils.getString(R.string.lib_network_error_502)
                    SERVICE_UNAVAILABLE -> ex.message =
                        StringUtils.getString(R.string.lib_network_error_503)
                    else -> ex.message = StringUtils.getString(R.string.lib_network_request_failed)
                }
                ex
            } else if (e is JsonParseException
                || e is JSONException
                || e is ParseException
            ) {
                ex = ResponseException(e, ERROR.PARSE_ERROR)
                ex.message = StringUtils.getString(R.string.lib_network_json_parse_exception)
                ex
            } else if (e is ConnectException) {
                ex = ResponseException(e, ERROR.NETWORK_ERROR)
                ex.message = StringUtils.getString(R.string.lib_network_request_failed)
                ex
            } else if (e is SSLHandshakeException) {
                ex = ResponseException(e, ERROR.SSL_ERROR)
                ex.message = StringUtils.getString(R.string.lib_network_ssl_exception)
                ex
            } else if (e is UnknownHostException) {
                ex = ResponseException(e, ERROR.HTTP_ERROR)
                ex.message = StringUtils.getString(R.string.lib_network_unknown_host)
                ex
            } else if (e is DataNullException) {
                ex = ResponseException(e, ERROR.DATA_NULL)
                ex.message = e.message
                ex
            } else if (e is ApiException) {
                ex = ResponseException(e, ERROR.DATA_NULL)
                ex.message = e.msg
                ex
            } else if (e is UnLoginException) {
                RouterUtil.launchLogin()
                ex = ResponseException(e, ERROR.UN_LOGIN)
                ex.message = StringUtils.getString(R.string.lib_network_please_log_in_first)
                ex
            } else {
                ex = ResponseException(e, ERROR.UNKNOWN)
                ex.message = StringUtils.getString(R.string.lib_network_unknown_exception)
                ex
            }
        } else {
            ex = ResponseException(e, ERROR.HTTP_ERROR)
            ex.message = StringUtils.getString(R.string.lib_network_Please_check_the_network)
            ex
        }


    }

    /**
     * 约定异常
     */
    object ERROR {
        //成功
        const val SUCCESS = 0

        //请求用户进行身份验证
        const val UN_LOGIN = 2001

        //数据为空
        const val DATA_NULL = 2002

        //未知错误
        const val UNKNOWN = 1000

        //解析错误
        const val PARSE_ERROR = 1001

        //网络错误
        const val NETWORK_ERROR = 1002

        //协议出错
        const val HTTP_ERROR = 1003

        //证书出错
        const val SSL_ERROR = 1005
    }

    /**
     * 统一异常类，便于处理
     */
    class ResponseException(throwable: Throwable, var code: Int) : Exception(throwable) {
        override var message: String = ""
    }
}