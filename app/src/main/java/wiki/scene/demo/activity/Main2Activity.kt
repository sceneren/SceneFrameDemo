package wiki.scene.demo.activity

import android.graphics.Color
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.aries.ui.view.tab.CommonTabLayout
import com.blankj.utilcode.util.LogUtils
import com.hjq.permissions.Permission
import wiki.scene.demo.R
import wiki.scene.lib_base.base_ac.FastMainActivity
import wiki.scene.lib_base.delegate.FastTabEntity
import wiki.scene.lib_common.router.RouterPath

@Route(path = RouterPath.Main.ACT_MAIN_2)
class Main2Activity : FastMainActivity() {
    private var mTabEntities = mutableListOf<FastTabEntity>()

    override fun getTabList(): MutableList<FastTabEntity> {
        val tab1Fragment = ARouter.getInstance()
            .build(RouterPath.Main.FRAG_TAB_1)
            .withInt("type", 1)
            .withString("name", "Test")
            .navigation() as Fragment
        mTabEntities.add(
            FastTabEntity(
                R.string.tab_1,
                R.drawable.ic_tab_1_d,
                R.drawable.ic_tab_1_s,
                tab1Fragment
            )
        )

        val tab2Fragment = ARouter.getInstance()
            .build(RouterPath.Main.FRAG_TAB_2)
            .navigation() as Fragment
        mTabEntities.add(
            FastTabEntity(
                R.string.tab_2,
                R.drawable.ic_tab_2_d,
                R.drawable.ic_tab_2_s,
                tab2Fragment
            )
        )

        val tab3Fragment = ARouter.getInstance()
            .build(RouterPath.Main.FRAG_TAB_3)
            .navigation() as Fragment

        mTabEntities.add(
            FastTabEntity(
                R.string.tab_3,
                R.drawable.ic_tab_3_d,
                R.drawable.ic_tab_3_s,
                tab3Fragment
            )
        )
        return mTabEntities
    }

    override fun hasTitleBarView(): Boolean {
        return false
    }

    override fun isDoubleClickExit(): Boolean {
        return true
    }

    override fun initEvents() {
        super.initEvents()
//        requestPermissions(Permission.MANAGE_EXTERNAL_STORAGE)
    }

    override fun reqPermissionFailure(permissions: List<String>) {
        super.reqPermissionFailure(permissions)
        LogUtils.e("reqPermissionFailure")
    }

    override fun setTabLayout(tabLayout: CommonTabLayout) {
        super.setTabLayout(tabLayout)
        tabLayout.delegate.textUnSelectColor = Color.parseColor("#5E607D")
        tabLayout.delegate.textSelectColor = Color.parseColor("#EA39B5")
    }

}