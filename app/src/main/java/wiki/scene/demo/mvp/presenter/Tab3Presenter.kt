package wiki.scene.demo.mvp.presenter

import wiki.scene.demo.mvp.contract.Tab3Contract
import wiki.scene.demo.mvp.model.Tab3Model
import wiki.scene.entity.ArticleListRes
import wiki.scene.lib_base.base_mvp.impl.BasePresenter
import wiki.scene.lib_network.exception.NetException
import wiki.scene.lib_network.ext.bindLifecycle
import wiki.scene.lib_network.observer.BaseObserver

class Tab3Presenter(mView: Tab3Contract.IView) :
    BasePresenter<Tab3Model, Tab3Contract.IView>(Tab3Model(), mView), Tab3Contract.IPresenter {

    override fun getArticleList(isFirst: Boolean, loadPage: Int) {
        mBaseModel.getArticleList(loadPage)
            .bindLifecycle(mBaseView.getLifecycleTransformer())
            .subscribe(object : BaseObserver<ArticleListRes>() {
                override fun onStart() {
                    super.onStart()
                    mBaseView.loadListDataStart(isFirst)
                }

                override fun onSuccess(data: ArticleListRes) {
                    mBaseView.loadListDataSuccess(
                        isFirst,
                        data.curPage,
                        data.pageCount,
                        data.datas
                    )
                }

                override fun onFail(e: NetException.ResponseException) {
                    mBaseView.loadListDataFail(isFirst, loadPage)
                }

            })
    }
}