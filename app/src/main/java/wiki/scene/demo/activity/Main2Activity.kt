package wiki.scene.demo.activity

import com.alibaba.android.arouter.facade.annotation.Route
import wiki.scene.demo.R
import wiki.scene.demo.fragment.Tab1Fragment
import wiki.scene.demo.fragment.Tab2Fragment
import wiki.scene.demo.fragment.Tab3Fragment
import wiki.scene.lib_base.base_ac.FastMainActivity
import wiki.scene.lib_base.constant.RouterPath
import wiki.scene.lib_base.entity.FastTabEntity

@Route(path = RouterPath.MAIN_ACT_MAIN_2)
class Main2Activity : FastMainActivity() {
    private var mTabEntities = mutableListOf<FastTabEntity>()

    override fun getTabList(): MutableList<FastTabEntity> {
        mTabEntities.add(
            FastTabEntity(
                R.string.tab_1,
                R.drawable.ic_tab_1_d,
                R.drawable.ic_tab_1_s,
                Tab1Fragment.newInstance()
            )
        )
        mTabEntities.add(
            FastTabEntity(
                R.string.tab_2,
                R.drawable.ic_tab_2_d,
                R.drawable.ic_tab_2_s,
                Tab2Fragment.newInstance()
            )
        )
        mTabEntities.add(
            FastTabEntity(
                R.string.tab_3,
                R.drawable.ic_tab_3_d,
                R.drawable.ic_tab_3_s,
                Tab3Fragment.newInstance()
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
}