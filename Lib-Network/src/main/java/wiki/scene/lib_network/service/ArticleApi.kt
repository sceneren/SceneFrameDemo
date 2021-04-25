package wiki.scene.lib_network.service

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import wiki.scene.entity.ArticleListRes
import wiki.scene.entity.BannerInfo
import wiki.scene.entity.base.BaseResponse
import wiki.scene.lib_network.constant.ApiConstant

interface ArticleApi {
    /**
     * 获取轮播图
     *
     * @return
     */
    @GET(ApiConstant.Article.BANNER)
    fun banner(): Observable<BaseResponse<MutableList<BannerInfo>>>

    /**
     * 获取文章列表
     *
     * @param page 页码，拼接在连接中，从0开始。
     * @return
     */
    @GET(ApiConstant.Article.ARTICLE)
    fun listArticle(@Path("page") page: Int): Observable<BaseResponse<ArticleListRes>>
}