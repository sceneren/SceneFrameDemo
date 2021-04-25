package wiki.scene.demo.mvp.contract

import io.reactivex.Observable
import wiki.scene.entity.ArticleBean
import wiki.scene.entity.ArticleListRes
import wiki.scene.entity.base.BaseResponse
import wiki.scene.lib_base.base_mvp.i.IBaseModel
import wiki.scene.lib_base.base_mvp.i.IBasePresenter
import wiki.scene.lib_base.base_mvp.i.IRecyclerViewBaseView

class MvpRecyclerViewActContract {
    interface IView : IRecyclerViewBaseView<ArticleBean> {

    }

    interface IModel : IBaseModel {
        fun getArticleList(loadPage: Int): Observable<BaseResponse<ArticleListRes>>
    }

    interface IPresenter : IBasePresenter {
        fun getArticleList(isFirst: Boolean, loadPage: Int)
    }
}