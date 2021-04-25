package wiki.scene.demo.mvp.presenter

import wiki.scene.demo.mvp.contract.Tab3Contract
import wiki.scene.demo.mvp.model.Tab3Model
import wiki.scene.entity.ArticleListRes
import wiki.scene.entity.base.BaseResponse
import wiki.scene.lib_base.base_mvp.impl.BasePresenter
import wiki.scene.lib_base.ext.bindLifecycle
import wiki.scene.lib_network.exception.NetException
import wiki.scene.lib_network.observer.BaseObserver

class Tab3Presenter(mView: Tab3Contract.IView) :
    BasePresenter<Tab3Model, Tab3Contract.IView>(Tab3Model(), mView), Tab3Contract.IPresenter {

    override fun getArticleList(isFirst: Boolean, loadPage: Int) {
        mBaseModel.getArticleList(loadPage)
            .bindLifecycle(mBaseView.getLifecycleTransformer())
            .subscribe(object : BaseObserver<BaseResponse<ArticleListRes>>() {
                override fun onStart() {
                    super.onStart()
                    mBaseView.loadListDataStart(isFirst)
                }

                override fun onSuccess(it: BaseResponse<ArticleListRes>) {
                    mBaseView.loadListDataSuccess(
                        isFirst,
                        it.data!!.curPage,
                        it.data!!.pageCount,
                        it.data!!.datas
                    )
                }

                override fun onFail(e: NetException.ResponseException) {
                    mBaseView.loadListDataFail(isFirst, loadPage)
                }

            })
    }
}