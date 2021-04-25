package wiki.scene.lib_network.helper

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable
import java.util.concurrent.TimeUnit

class RetrofitCreateHelper private constructor() : Serializable {
    companion object {
        private const val TIMEOUT_READ = 60
        private const val TIMEOUT_CONNECTION = 60

        @JvmStatic
        fun getInstance(): RetrofitCreateHelper {
            return SingletonHolder.mInstance
        }
    }

    private object SingletonHolder {
        //静态内部类
        val mInstance: RetrofitCreateHelper = RetrofitCreateHelper()
    }

    private fun readResolve(): Any {//防止单例对象在反序列化时重新生成对象
        return SingletonHolder.mInstance
    }

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

}