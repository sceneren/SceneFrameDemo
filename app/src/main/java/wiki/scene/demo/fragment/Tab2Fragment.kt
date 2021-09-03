package wiki.scene.demo.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ImageUtils
import com.hjq.bar.TitleBar
import wiki.scene.demo.R
import wiki.scene.demo.databinding.FragTab2Binding
import wiki.scene.lib_base.base_fg.BaseFg
import wiki.scene.lib_common.router.RouterPath

@Route(path = RouterPath.Main.FRAG_TAB_2)
class Tab2Fragment : BaseFg<FragTab2Binding>() {
    @JvmField
    @Autowired
    var color: String? = null

    override fun initToolBarView(titleBarView: TitleBar) {
        super.initToolBarView(titleBarView)
        titleBarView.title = ("TAB2")
        titleBarView.rightTitle = "测试"
    }

    override fun onTitleRightClick() {
        super.onTitleRightClick()
        showToast("测试")
    }

    override fun hasTitleBarView(): Boolean {
        return true
    }

    override fun initViews() {
        super.initViews()
    }

    override fun loadData() {

    }
}