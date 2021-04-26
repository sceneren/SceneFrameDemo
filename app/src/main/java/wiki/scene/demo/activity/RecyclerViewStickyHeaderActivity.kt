package wiki.scene.demo.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.aries.ui.view.title.TitleBarView
import wiki.scene.demo.databinding.ActRecyclerviewStickyHeaderBinding
import wiki.scene.lib_base.base_ac.BaseAc
import wiki.scene.lib_common.router.RouterPath

@Route(path = RouterPath.Main.ACT_RECYCLERVIEW_STICKY_HEADER)
class RecyclerViewStickyHeaderActivity : BaseAc<ActRecyclerviewStickyHeaderBinding>() {

    override fun initToolBarView(titleBarView: TitleBarView) {
        super.initToolBarView(titleBarView)
        titleBarView.setTitleMainText("吸顶的RecyclerView")
    }

    override fun initViews() {
        super.initViews()

    }
}