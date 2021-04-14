package wiki.scene.demo.mvp.contract

import androidx.lifecycle.LiveData
import wiki.scene.lib_base.base_api.res_data.ArticleBean
import wiki.scene.lib_base.base_api.res_data.ArticleListRes
import wiki.scene.lib_base.base_mvp.i.IBaseModel
import wiki.scene.lib_base.base_mvp.i.IBasePresenter
import wiki.scene.lib_base.base_mvp.i.IRecyclerViewBaseView
import wiki.scene.lib_network.bean.ApiResponse

class Tab3Contract {
    interface IView : IRecyclerViewBaseView<ArticleBean> {

    }

    interface IModel : IBaseModel {
        fun getArticleList(loadPage: Int): LiveData<ApiResponse<ArticleListRes>>
    }

    interface IPresenter : IBasePresenter {
        fun getArticleList(isFirst: Boolean, loadPage: Int)
    }
}