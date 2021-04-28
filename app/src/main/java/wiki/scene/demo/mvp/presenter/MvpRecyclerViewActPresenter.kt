package wiki.scene.demo.mvp.presenter

import wiki.scene.demo.mvp.contract.MvpRecyclerViewActContract
import wiki.scene.demo.mvp.model.MvpRecyclerViewActModel
import wiki.scene.entity.ArticleListRes
import wiki.scene.lib_base.base_mvp.impl.BasePresenter
import wiki.scene.lib_network.exception.NetException
import wiki.scene.lib_network.ext.bindLifecycle
import wiki.scene.lib_network.observer.BaseObserver

class MvpRecyclerViewActPresenter(mView: MvpRecyclerViewActContract.IView) :
    BasePresenter<MvpRecyclerViewActModel, MvpRecyclerViewActContract.IView>(
        MvpRecyclerViewActModel(),
        mView
    ), MvpRecyclerViewActContract.IPresenter {

    override fun getArticleList(isFirst: Boolean, loadPage: Int) {
        mBaseModel.getArticleList(loadPage)
            .bindLifecycle(getLifecycle())
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