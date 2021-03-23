package wiki.scene.lib_network.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * @date: 2019\5\29 0029
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description: 公共参数拦截器
 */
class BodyInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val selfID = ""
        val token = ""
        val url = originalRequest.url
            .newBuilder()
            .addQueryParameter("userId", selfID)
            .build()
        Log.e("TAG", "统一参数： $selfID   $token")
        val authorised = originalRequest.newBuilder()
            .header("Authorization", selfID + token)
            .url(url)
            .build()
        return chain.proceed(authorised)
    }
}