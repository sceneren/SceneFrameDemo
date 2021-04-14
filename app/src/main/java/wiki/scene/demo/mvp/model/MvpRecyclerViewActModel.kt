package wiki.scene.demo.mvp.model

import androidx.lifecycle.LiveData
import wiki.scene.demo.mvp.contract.Tab3Contract
import wiki.scene.lib_base.base_api.res_data.ArticleListRes
import wiki.scene.lib_base.base_api.util.ApiUtil
import wiki.scene.lib_base.base_mvp.impl.BaseModel
import wiki.scene.lib_network.bean.ApiResponse

class MvpRecyclerViewActModel : BaseModel(), Tab3Contract.IModel {
    override fun getArticleList(
        loadPage: Int
    ): LiveData<ApiResponse<ArticleListRes>> {
        return ApiUtil.articleApi
            .listArticle(loadPage)
    }

}