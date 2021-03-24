package wiki.scene.lib_network.interceptor

import com.blankj.utilcode.util.LogUtils
import okhttp3.*
import okhttp3.internal.http.promisesBody
import wiki.scene.lib_network.util.IOUtils
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/**
 * ================================================
 * 描    述：OkHttp拦截器，主要用于打印日志
 * 修订历史：
 * ================================================
 */
class HttpLoggingInterceptor : Interceptor {
    @Volatile
    private var printLevel: Level? = Level.NONE
    private var colorLevel: java.util.logging.Level? = null

    enum class Level {
        NONE,  //不打印log
        BASIC,  //只打印 请求首行 和 响应首行
        HEADERS,  //打印请求和响应的所有 Header
        BODY //所有数据全部打印
    }

    fun setPrintLevel(level: Level) {
        if (printLevel == null) throw NullPointerException("printLevel == null. Use Level.NONE instead.")
        printLevel = level
    }

    fun setColorLevel(level: java.util.logging.Level?) {
        colorLevel = level
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        if (printLevel == Level.NONE) {
            return chain.proceed(request)
        }

        //请求日志拦截
        logForRequest(request, chain.connection())

        //执行请求，计算请求时间
        val startNs = System.nanoTime()
        val response: Response
        response = try {
            chain.proceed(request)
        } catch (e: Exception) {
            LogUtils.i("<-- HTTP FAILED: $e")
            throw e
        }
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        //响应日志拦截
        return logForResponse(response, tookMs)
    }

    @Throws(IOException::class)
    private fun logForRequest(request: Request, connection: Connection?) {
        val logBody = printLevel == Level.BODY
        val logHeaders = printLevel == Level.BODY || printLevel == Level.HEADERS
        val requestBody = request.body
        val hasRequestBody = requestBody != null
        val protocol = connection?.protocol() ?: Protocol.HTTP_1_1
        try {
            val requestStartMessage = "--> " + request.method + ' ' + request.url + ' ' + protocol
            LogUtils.i(requestStartMessage)
            if (logHeaders) {
                if (hasRequestBody) {
                    // Request body headers are only present when installed as a network interceptor. Force
                    // them to be included (when available) so there values are known.
                    if (requestBody!!.contentType() != null) {
                        LogUtils.i(
                            """
    
    Content-Type: ${requestBody.contentType()}
    """.trimIndent()
                        )
                    }
                    if (requestBody.contentLength() != -1L) {
                        LogUtils.i(
                            """
    
    Content-Length: ${requestBody.contentLength()}
    """.trimIndent()
                        )
                    }
                }
                val headers = request.headers
                var i = 0
                val count = headers.size
                while (i < count) {
                    val name = headers.name(i)
                    // Skip headers from the request body as they are explicitly logged above.
                    if (!"Content-Type".equals(name, ignoreCase = true) && !"Content-Length".equals(
                            name,
                            ignoreCase = true
                        )
                    ) {
                        LogUtils.i(
                            """
    
    $name: ${headers.value(i)}
    """.trimIndent()
                        )
                    }
                    i++
                }
                LogUtils.i("------------------------")
                if (logBody && hasRequestBody) {
                    LogUtils.i(
                        "isPlaintext(requestBody.contentType())=" + isPlaintext(
                            requestBody!!.contentType()
                        )
                    )
                    if (isPlaintext(requestBody.contentType())) {
                        LogUtils.i("request=" + bodyToString(request))
                    } else {
                        LogUtils.i("\nbody: maybe [binary body], omitted!")
                    }
                }
            }
        } catch (e: Exception) {
        } finally {
            LogUtils.i("--> END " + request.method)
        }
    }

    private fun logForResponse(response: Response, tookMs: Long): Response {
        val builder: Response.Builder = response.newBuilder()
        val clone: Response = builder.build()
        var responseBody = clone.body
        val logBody = printLevel == Level.BODY
        val logHeaders = printLevel == Level.BODY || printLevel == Level.HEADERS
        try {
            LogUtils.i("<-- " + clone.code + ' ' + clone.message + ' ' + clone.request.url + " (" + tookMs + "ms）")
            if (logHeaders) {
                val headers = clone.headers
                var i = 0
                val count = headers.size
                while (i < count) {
                    LogUtils.i(
                        """
    
    ${headers.name(i)}: ${headers.value(i)}
    """.trimIndent()
                    )
                    i++
                }
                LogUtils.i(" ")
                if (logBody && clone.promisesBody()) {
                    if (responseBody == null) return response
                    if (isPlaintext(responseBody.contentType())) {
                        val bytes = IOUtils.toByteArray(responseBody.byteStream())
                        val contentType = responseBody.contentType()
                        val body = String(bytes, getCharset(contentType)!!)
                        responseBody = ResponseBody.create(responseBody.contentType(), bytes)
                        return response.newBuilder().body(responseBody).build()
                    } else {
                        LogUtils.i("\nbody: maybe [binary body], omitted!")
                    }
                }
            }
        } catch (e: Exception) {
        } finally {
            LogUtils.i("<-- END HTTP")
        }
        return response
    }

    private fun bodyToString(request: Request): String? {
        try {
            val copy = request.newBuilder().build()
            val body = copy.body ?: return null
            return body.toString()
            //            Buffer buffer = new Buffer();
//            body.writeTo(buffer);
//            Charset charset = getCharset(body.contentType());
        } catch (e: Exception) {
        }
        return null
    }

    companion object {
        private val UTF8 = Charset.forName("UTF-8")
        private fun getCharset(contentType: MediaType?): Charset? {
            var charset = if (contentType != null) contentType.charset(UTF8) else UTF8
            if (charset == null) charset = UTF8
            return charset
        }

        /**
         * Returns true if the body in question probably contains human readable text. Uses a small sample
         * of code points to detect unicode control characters commonly used in binary file signatures.
         */
        private fun isPlaintext(mediaType: MediaType?): Boolean {
            if (mediaType == null) return false
            if (mediaType.type == "text") {
                return true
            }
            var subtype = mediaType.subtype
            subtype = subtype.toLowerCase()
            if (subtype.contains("x-www-form-urlencoded") || subtype.contains("json") || subtype.contains(
                    "xml"
                ) || subtype.contains("html")
            ) //
                return true
            return false
        }
    }
}