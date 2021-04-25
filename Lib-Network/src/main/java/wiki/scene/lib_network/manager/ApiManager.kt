package wiki.scene.lib_network.manager

import wiki.scene.lib_network.constant.ApiConstant
import wiki.scene.lib_network.helper.RetrofitCreateHelper
import wiki.scene.lib_network.service.ArticleApi
import wiki.scene.lib_network.service.LoginApi
import java.io.Serializable

class ApiManager private constructor() : Serializable {

    private var articleApi: ArticleApi? = null
    private var loginApi: LoginApi? = null

    companion object {
        @JvmStatic
        fun getInstance(): ApiManager {
            return SingletonHolder.mInstance
        }
    }

    private object SingletonHolder {
        //静态内部类
        val mInstance: ApiManager = ApiManager()
    }

    private fun readResolve(): Any {//防止单例对象在反序列化时重新生成对象
        return SingletonHolder.mInstance
    }

    fun articleApi(): ArticleApi {
        if (articleApi == null) {
            articleApi = RetrofitCreateHelper.getInstance()
                .create(ApiConstant.BASE_URL, ArticleApi::class.java)
        }
        return articleApi!!
    }

    fun loginApi(): LoginApi {
        if (loginApi == null) {
            loginApi = RetrofitCreateHelper.getInstance()
                .create(ApiConstant.BASE_URL, LoginApi::class.java)
        }
        return loginApi!!
    }
}