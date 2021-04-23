package wiki.scene.lib_base.base_api.util

import wiki.scene.lib_base.base_api.module.ArticleApi
import wiki.scene.lib_base.base_api.module.LoginApi
import wiki.scene.lib_network.constrant.U
import wiki.scene.lib_network.livedata.RetrofitCreateLiveDataHelper

/**
 * Created by zlx on 2020/9/28 15:09
 * Email: 1170762202@qq.com
 * Description: 不同模块BASE_URL可能不同
 */
object ApiUtil {

    val articleApi: ArticleApi
        get() = RetrofitCreateLiveDataHelper.getInstance()
            .create(U.BASE_URL, ArticleApi::class.java)

    val loginAny: LoginApi
        get() = RetrofitCreateLiveDataHelper.getInstance()
            .create(U.BASE_URL, LoginApi::class.java)
}