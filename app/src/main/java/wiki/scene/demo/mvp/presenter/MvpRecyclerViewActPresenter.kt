package wiki.scene.demo.mvp.presenter

import wiki.scene.demo.mvp.contract.MvpRecyclerViewActContract
import wiki.scene.demo.mvp.contract.Tab3Contract
import wiki.scene.demo.mvp.model.MvpRecyclerViewActModel
import wiki.scene.demo.mvp.model.Tab3Model
import wiki.scene.lib_base.base_api.res_data.ArticleListRes
import wiki.scene.lib_base.base_mvp.impl.BasePresenter
import wiki.scene.lib_network.bean.ApiResponse
import wiki.scene.lib_network.livedata.FastObserver

class MvpRecyclerViewActPresenter(mView: MvpRecyclerViewActContract.IView) :
    BasePresenter<MvpRecyclerViewActModel, MvpRecyclerViewActContract.IView>(MvpRecyclerViewActModel(), mView), MvpRecyclerViewActContract.IPresenter {

    override fun getArticleList(isFirst: Boolean, loadPage: Int) {
        mBaseModel.getArticleList(loadPage)
            .observe(bindToLifeCycle(), object : FastObserver<ArticleListRes>() {
                override fun onStart() {
                    super.onStart()
                    mBaseView.loadListDataStart(isFirst)
                }

                override fun onSuccess(data: ApiResponse<ArticleListRes>) {
                    data.data?.let {
                        mBaseView.loadListDataSuccess(isFirst, it.curPage, it.total, it.datas!!)
                    }
                }

                override fun onFail(msg: String?) {
                    super.onFail(msg)
                    mBaseView.loadListDataFail(isFirst, loadPage)
                }

            })
    }
}