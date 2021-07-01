package wiki.scene.demo.activity

import android.util.SparseArray
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import dagger.hilt.android.AndroidEntryPoint
import wiki.scene.demo.R
import wiki.scene.lib_base.base_ac.FastMainActivity
import wiki.scene.lib_base.indicator.entity.CustomBottomTabInfo
import wiki.scene.lib_common.router.RouterPath

@AndroidEntryPoint
@Route(path = RouterPath.Main.ACT_MAIN)
class MainActivity : FastMainActivity() {
    override fun getFragmentList(): SparseArray<Fragment> {
        val fragments = SparseArray<Fragment>()
        fragments.append(
            0, ARouter.getInstance()
                .build(RouterPath.Main.FRAG_TAB_1)
                .withInt("type", 1)
                .withString("name", "Test")
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


}