package wiki.scene.lib_network.interceptor

import com.blankj.utilcode.util.LogUtils
import okhttp3.*
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset
import java.util.*

/**
 * date: 2019\6\11 0011
 * author: zlx
 * email: 1170762202@qq.com
 * description: log 拦截
 */
class LogInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        printParams(request.body)
        val t1 = System.nanoTime()
        val response: Response = chain.proceed(chain.request())
        val t2 = System.nanoTime()
        LogUtils.i(
            String.format(
                Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                response.request.url, (t2 - t1) / 1e6, response.headers
            )
        )
        val mediaType = response.body!!.contentType()
        val content = response.body!!.string()
        LogUtils.i("response body:$content")
        return response.newBuilder()
            .body(ResponseBody.create(mediaType, content))
            .build()
    }

    private fun printParams(body: RequestBody?) {
        if (body == null) {
            return
        }
        val buffer = Buffer()
        try {
            body.writeTo(buffer)
            var charset = Charset.forName("UTF-8")
            val contentType = body.contentType()
            if (contentType != null) {
                charset = contentType.charset(charset)
            }
            val params = buffer.readString(charset!!)
            LogUtils.e("请求参数： | $params")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}