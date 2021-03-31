package wiki.scene.demo.fragment

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.aries.ui.view.title.TitleBarView
import com.blankj.utilcode.util.LogUtils
import wiki.scene.demo.databinding.FragTab1Binding
import wiki.scene.lib_base.base_fg.BaseFg
import wiki.scene.lib_base.constant.RouterPath

@Route(path = RouterPath.Main.FRAG_TAB_1)
class Tab1Fragment : BaseFg<FragTab1Binding>() {
    @JvmField
    @Autowired
    var type = 0

    @JvmField
    @Autowired
    var name = ""

    override fun initViews() {
        super.initViews()
        binding.btnTab.setOnClickListener {
            ARouter.getInstance()
                .build(RouterPath.Main.ACT_MAIN)
                .navigation()
        }
        binding.btnTest.setOnClickListener {
            ARouter.getInstance()
                .build(RouterPath.Main.ACT_RECYCLERVIEW)
                .navigation()
        }

        binding.btnRecyclerViewStickyHeader.setOnClickListener {
            ARouter.getInstance()
                .build(RouterPath.Main.ACT_RECYCLERVIEW_STICKY_HEADER)
                .navigation()
        }

    }

    override fun initToolBarView(titleBarView: TitleBarView) {
        super.initToolBarView(titleBarView)
        titleBarView.setTitleMainText("主页")
    }

    override fun hasTitleBarView(): Boolean {
        return true
    }

    override fun loadData() {
        super.loadData()
        LogUtils.e("loadData")
    }
}