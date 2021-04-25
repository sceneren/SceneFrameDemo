package wiki.scene.demo.mvp.model

import io.reactivex.Observable
import wiki.scene.demo.mvp.contract.Tab3Contract
import wiki.scene.entity.ArticleListRes
import wiki.scene.entity.base.BaseResponse
import wiki.scene.lib_base.base_mvp.impl.BaseModel
import wiki.scene.lib_base.ext.changeNew2MainThread
import wiki.scene.lib_network.manager.ApiManager

class MvpRecyclerViewActModel : BaseModel(), Tab3Contract.IModel {
    override fun getArticleList(loadPage: Int): Observable<BaseResponse<ArticleListRes>> {
        return ApiManager.getInstance()
            .articleApi()
            .listArticle(loadPage)
            .changeNew2MainThread()
    }
}