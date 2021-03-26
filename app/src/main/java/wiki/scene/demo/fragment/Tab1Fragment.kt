package wiki.scene.demo.fragment

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.aries.ui.view.title.TitleBarView
import wiki.scene.demo.databinding.FragTab1Binding
import wiki.scene.lib_base.base_fg.BaseFg
import wiki.scene.lib_base.constant.RouterPath

class Tab1Fragment : BaseFg<FragTab1Binding>() {
    companion object {
        fun newInstance(): Tab1Fragment {
            val args = Bundle()
            val fragment = Tab1Fragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initViews() {
        super.initViews()
        binding.btnTab.setOnClickListener {
            ARouter.getInstance()
                .build(RouterPath.MAIN_ACT_MAIN)
                .navigation()
        }
        binding.btnTest.setOnClickListener {
            ARouter.getInstance()
                .build(RouterPath.MAIN_ACT_RECYCLERVIEW)
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
}