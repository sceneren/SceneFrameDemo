package wiki.scene.lib_network.interceptor

import okhttp3.*
import java.io.IOException

class BaseInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        //POST请求
        return if (request.method == "POST") {
            val bodyBuilder = FormBody.Builder()
            if (request.body is FormBody) {
                var formBody = request.body as FormBody
                //把原来的参数添加到新的构造器，（因为没找到直接添加，所以就new新的）
                for (i in 0 until formBody.size) {
                    bodyBuilder.add(formBody.name(i), formBody.value(i))
                }
                //添加公共参数
                formBody = bodyBuilder
//                    .add("pubParam1", "1")
//                    .add("pubParam2", "2")
//                    .add("pubParam3", "3")
                    .build()

                //添加请求头
                request = request
                    .newBuilder()
                    .post(formBody)
                    .addHeader("Content-Type", "application/json;charset=UTF-8")
                    .addHeader("User-Agent", "android")
                    .build()
            }
            chain.proceed(request)
        } else {
            //添加公共参数
            val urlBuilder: HttpUrl.Builder = request.url
                .newBuilder()
//                .addQueryParameter("pubParam1", "1")
//                .addQueryParameter("pubParam2", "2")
//                .addQueryParameter("pubParam3", "3")

            //添加请求头
            val newBuilder: Request.Builder = request.newBuilder()
                .method(request.method, request.body)
                .url(urlBuilder.build())
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .addHeader("User-Agent", "android")
            chain.proceed(newBuilder.build())
        }
    }
}