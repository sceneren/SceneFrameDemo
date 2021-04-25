package wiki.scene.demo.mvp.model

import io.reactivex.Observable
import wiki.scene.demo.mvp.contract.Tab3Contract
import wiki.scene.entity.ArticleListRes
import wiki.scene.lib_base.base_mvp.impl.BaseModel
import wiki.scene.lib_network.manager.ApiManager
import wiki.scene.lib_network.transform.ApiTransform

class MvpRecyclerViewActModel : BaseModel(), Tab3Contract.IModel {
    override fun getArticleList(loadPage: Int): Observable<ArticleListRes> {
        return ApiTransform.transform(
            ApiManager.getInstance()
                .articleApi()
                .listArticle(loadPage)
        )

    }
}