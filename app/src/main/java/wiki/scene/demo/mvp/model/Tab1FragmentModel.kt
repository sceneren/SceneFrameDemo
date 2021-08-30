package wiki.scene.demo.mvp.model

import io.reactivex.Observable
import wiki.scene.demo.mvp.contract.Tab1FragmentContract
import wiki.scene.entity.BannerInfo
import wiki.scene.lib_base.base_mvp.impl.BaseModel
import wiki.scene.lib_network.ext.transformData
import wiki.scene.lib_network.manager.ApiManager

class Tab1FragmentModel : BaseModel(), Tab1FragmentContract.IModel {
    override fun banner(): Observable<MutableList<BannerInfo>> {
        return ApiManager.getInstance()
            .articleApi()
            .banner()
            .transformData()
    }

}
