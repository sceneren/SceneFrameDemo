package wiki.scene.lib_network.api2

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import wiki.scene.lib_network.interceptor.HttpLoggingInterceptor
import wiki.scene.lib_network.service.ApiService
import java.util.concurrent.TimeUnit

/**
 * Created by Zlx on 2017/12/12.
 */
class RetrofitCreateHelper private constructor() {
    fun <T> create(baseURL: String, service: Class<T>): T {
        return initRetrofit(baseURL, initOkHttp()).create(service)
    }

    /**
     * 初始化Retrofit
     */
    private fun initRetrofit(baseURL: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseURL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * 初始化okhttp
     */
    private fun initOkHttp(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .readTimeout(TIMEOUT_CONNECTION.toLong(), TimeUnit.SECONDS) //设置读取超时时间
            .connectTimeout(TIMEOUT_READ.toLong(), TimeUnit.SECONDS) //设置请求超时时间
            .writeTimeout(TIMEOUT_READ.toLong(), TimeUnit.SECONDS) //设置写入超时时间
            .retryOnConnectionFailure(true) //设置出现错误进行重新连接。
            //失败重连
            .retryOnConnectionFailure(true)
            .addInterceptor(HttpLoggingInterceptor()) //添加打印拦截器
            .build()
    }

    companion object {
        private const val TIMEOUT_READ = 60
        private const val TIMEOUT_CONNECTION = 60
        private val mApiUrl: ApiService? = null
        var instance: RetrofitCreateHelper? = null
            get() {
                if (field == null) {
                    synchronized(RetrofitCreateHelper::class.java) {
                        field = RetrofitCreateHelper()
                    }
                }
                return field
            }
            private set
    }
}