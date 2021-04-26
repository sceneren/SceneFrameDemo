package wiki.scene.demo.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.aries.ui.view.title.TitleBarView
import wiki.scene.demo.databinding.FragTab2Binding
import wiki.scene.lib_base.base_fg.BaseFg
import wiki.scene.lib_common.router.RouterPath

@Route(path = RouterPath.Main.FRAG_TAB_2)
class Tab2Fragment : BaseFg<FragTab2Binding>() {
    override fun initToolBarView(titleBarView: TitleBarView) {
        super.initToolBarView(titleBarView)
        titleBarView.setTitleMainText("TAB2")
    }

    override fun hasTitleBarView(): Boolean {
        return true
    }

    override fun loadData() {

    }
}