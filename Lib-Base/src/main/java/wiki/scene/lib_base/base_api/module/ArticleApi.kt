package wiki.scene.lib_base.base_api.module

import androidx.lifecycle.LiveData
import retrofit2.http.GET
import retrofit2.http.Path
import wiki.scene.lib_base.base_api.res_data.ArticleListRes
import wiki.scene.lib_base.base_api.res_data.BannerInfo
import wiki.scene.lib_network.bean.ApiResponse

/**
 * Copyright (C)
 * FileName: ArticleApi
 * Author: Zlx
 * Email: 1170762202@qq.com
 * Date: 2020/9/17 10:48
 * Description: 文章api
 */
interface ArticleApi {
    /**
     * 获取轮播图
     *
     * @return
     */
    @get:GET("banner/json")
    val banner: LiveData<ApiResponse<List<BannerInfo>>>

    /**
     * 获取文章列表
     *
     * @param page 页码，拼接在连接中，从0开始。
     * @return
     */
    @GET("article/list/{page}/json")
    fun listArticle(@Path("page") page: Int): LiveData<ApiResponse<ArticleListRes>>
}