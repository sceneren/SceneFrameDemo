package wiki.scene.demo.activity

import android.graphics.Color
import android.util.SparseArray
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.Subscribe
import wiki.scene.demo.R
import wiki.scene.lib_base.base_ac.FastMainActivity
import wiki.scene.lib_base.indicator.entity.CustomBottomTabInfo
import wiki.scene.lib_common.router.RouterPath
import wiki.scene.module_scan.event.OnScanResultEvent

@AndroidEntryPoint
@Route(path = RouterPath.Main.ACT_MAIN)
class MainActivity : FastMainActivity() {
    override fun getFragmentList(): SparseArray<Fragment> {
        val fragments = SparseArray<Fragment>()
        fragments.append(
            0, ARouter.getInstance()
                .build(RouterPath.Main.FRAG_TAB_1)
                .navigation() as Fragment
        )
        fragments.append(
            1, ARouter.getInstance()
                .build(RouterPath.Main.FRAG_TAB_2)
                .withString("color", "#FF00C4")
                .navigation() as Fragment
        )

        fragments.append(
            2, ARouter.getInstance()
                .build(RouterPath.Main.FRAG_TAB_3)
                .navigation() as Fragment
        )
        return fragments
    }

    override fun getTabList(): List<CustomBottomTabInfo> {
        val customBottomTabInfo1 =
            CustomBottomTabInfo("Tab1", R.drawable.ic_tab_1_d, R.drawable.ic_tab_1_s)
        val customBottomTabInfo2 =
            CustomBottomTabInfo("Tab2", R.drawable.ic_tab_2_d, R.drawable.ic_tab_2_s)
        val customBottomTabInfo3 =
            CustomBottomTabInfo("Tab3", R.drawable.ic_tab_3_d, R.drawable.ic_tab_3_s)
        return listOf(customBottomTabInfo1, customBottomTabInfo2, customBottomTabInfo3)
    }

    override fun getDividerColor(): Int {
        return Color.parseColor("#666666")
    }

    override fun getDividerHeight(): Int {
        return 1
    }

    override fun allowEventBus(): Boolean {
        return true
    }

    @Subscribe
    fun onEvent(event: OnScanResultEvent?) {
        event?.let {
            LogUtils.e(event.tag + "====" + event.result)
        }
    }
}