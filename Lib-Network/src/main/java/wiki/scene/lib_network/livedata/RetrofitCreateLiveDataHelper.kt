package wiki.scene.lib_network.livedata

import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import wiki.scene.lib_common.provider.AppProvider
import wiki.scene.lib_network.interceptor.LogInterceptor
import java.io.Serializable
import java.util.concurrent.TimeUnit

class RetrofitCreateLiveDataHelper private constructor() : Serializable {
    companion object {

        private const val TIMEOUT_READ = 60
        private const val TIMEOUT_CONNECTION = 60

        @JvmStatic
        fun getInstance(): RetrofitCreateLiveDataHelper {
            return SingletonHolder.mInstance
        }
    }

    private object SingletonHolder {
        //静态内部类
        val mInstance: RetrofitCreateLiveDataHelper = RetrofitCreateLiveDataHelper()
    }

    private fun readResolve(): Any {//防止单例对象在反序列化时重新生成对象
        return SingletonHolder.mInstance
    }

    fun <T> create(baseURL: String, service: Class<T>): T {
        return initRetrofitWithLiveData(baseURL, initOkHttp()).create(service)
    }

    /**
     * 初始化Retrofit
     */
    private fun initRetrofitWithLiveData(baseURL: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseURL)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
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
            .cookieJar(cookieJar!!)
            .addInterceptor(LogInterceptor()) //添加打印拦截器
            .build()
    }

    var cookieJar: ClearableCookieJar? = null
        get() {
            if (field == null) {
                field = PersistentCookieJar(
                    SetCookieCache(),
                    SharedPrefsCookiePersistor(AppProvider.instance.app)
                )
            }
            return field!!
        }
        private set

}