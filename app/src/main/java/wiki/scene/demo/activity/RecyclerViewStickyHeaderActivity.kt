package wiki.scene.demo.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.hjq.bar.TitleBar
import wiki.scene.demo.databinding.ActRecyclerviewStickyHeaderBinding
import wiki.scene.lib_base.base_ac.BaseAc
import wiki.scene.lib_common.router.RouterPath

@Route(path = RouterPath.Main.ACT_RECYCLERVIEW_STICKY_HEADER)
class RecyclerViewStickyHeaderActivity : BaseAc<ActRecyclerviewStickyHeaderBinding>() {

    override fun initToolBarView(titleBarView: TitleBar) {
        super.initToolBarView(titleBarView)
        titleBarView.title = ("吸顶的RecyclerView")
    }

    override fun initViews() {
        super.initViews()

    }
}