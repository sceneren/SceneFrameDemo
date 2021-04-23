package wiki.scene.demo.mvp.presenter

import wiki.scene.demo.mvp.contract.Tab3Contract
import wiki.scene.demo.mvp.model.Tab3Model
import wiki.scene.lib_base.base_api.res_data.ArticleListRes
import wiki.scene.lib_base.base_mvp.impl.BasePresenter
import wiki.scene.lib_network.bean.ApiResponse
import wiki.scene.lib_network.livedata.FastObserver

class Tab3Presenter(mView: Tab3Contract.IView) :
    BasePresenter<Tab3Model, Tab3Contract.IView>(Tab3Model(), mView), Tab3Contract.IPresenter {

    override fun getArticleList(isFirst: Boolean, loadPage: Int) {
        mBaseModel.getArticleList(loadPage)
            .observe(bindToLifeCycle(), object : FastObserver<ArticleListRes>() {
                override fun onStart() {
                    super.onStart()
                    mBaseView.loadListDataStart(isFirst)
                }

                override fun onSuccess(data: ApiResponse<ArticleListRes>) {
                    data.data?.let {
                        mBaseView.loadListDataSuccess(isFirst, it.curPage, it.pageCount, it.datas)
                    }
                }

                override fun onFail(msg: String?) {
                    super.onFail(msg)
                    mBaseView.loadListDataFail(isFirst, loadPage)
                }

            })
    }
}