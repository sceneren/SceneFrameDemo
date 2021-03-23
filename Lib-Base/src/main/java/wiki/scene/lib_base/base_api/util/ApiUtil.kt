package wiki.scene.lib_base.base_api.util

import wiki.scene.lib_base.base_api.module.*
import wiki.scene.lib_network.constrant.U
import wiki.scene.lib_network.livedata.RetrofitCreateLiveDataHelper.Companion.instance

/**
 * Created by zlx on 2020/9/28 15:09
 * Email: 1170762202@qq.com
 * Description: 不同模块BASE_URL可能不同
 */
object ApiUtil {
    val projectApi: ProjectApi
        get() = instance!!.create(U.BASE_URL, ProjectApi::class.java)
    val articleApi: ArticleApi
        get() = instance!!.create(U.BASE_URL, ArticleApi::class.java)
    val treeApi: TreeApi
        get() = instance!!.create(U.BASE_URL, TreeApi::class.java)
    val loginApi: LoginApi
        get() = instance!!.create(U.BASE_URL, LoginApi::class.java)
    val userApi: UserApi
        get() = instance!!.create(U.BASE_URL, UserApi::class.java)
}