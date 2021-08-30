package wiki.scene.demo.mvp.contract

import io.reactivex.Observable
import wiki.scene.entity.BannerInfo
import wiki.scene.lib_base.base_mvp.i.IBaseModel
import wiki.scene.lib_base.base_mvp.i.IBaseView

class Tab1FragmentContract {

    interface IView : IBaseView {
        fun bindBanner(data: MutableList<BannerInfo>)
    }

    interface IModel : IBaseModel {
        fun banner(): Observable<MutableList<BannerInfo>>
    }

}
