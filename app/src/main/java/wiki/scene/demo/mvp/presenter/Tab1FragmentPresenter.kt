package wiki.scene.demo.mvp.presenter

import wiki.scene.demo.mvp.contract.Tab1FragmentContract
import wiki.scene.demo.mvp.model.Tab1FragmentModel
import wiki.scene.entity.BannerInfo
import wiki.scene.lib_base.base_mvp.impl.BasePresenter
import wiki.scene.lib_network.exception.NetException
import wiki.scene.lib_network.ext.bindLifecycle
import wiki.scene.lib_network.observer.BaseLoadingObserver
import wiki.scene.lib_network.observer.BaseObserver

class Tab1FragmentPresenter(mView: Tab1FragmentContract.IView) :
    BasePresenter<Tab1FragmentModel, Tab1FragmentContract.IView>(Tab1FragmentModel(), mView) {

    fun banner() {
        mBaseModel.banner()
            .bindLifecycle(getLifecycle())
            .subscribe(object : BaseObserver<MutableList<BannerInfo>>() {
                override fun onSuccess(data: MutableList<BannerInfo>) {
                    mBaseView.bindBanner(data)
                }

                override fun onFail(e: NetException.ResponseException) {

                }
            })
    }
}
