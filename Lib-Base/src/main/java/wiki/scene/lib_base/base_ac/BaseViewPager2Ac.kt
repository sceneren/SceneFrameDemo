package wiki.scene.lib_base.base_ac

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import wiki.scene.lib_base.indicator.adapter.TopCommonNavigatorAdapter
import wiki.scene.lib_base.indicator.adapter.ViewPager2Adapter
import wiki.scene.lib_base.indicator.ext.bind

abstract class BaseViewPager2Ac<VB : ViewBinding> : BaseAc<VB>() {

    abstract fun getViewPager2(): ViewPager2
    abstract fun getMagicIndicator(): MagicIndicator
    abstract fun getTabList(): List<String>
    abstract fun getFragmentList(): SparseArray<Fragment>

    fun getCommonNavigatorAdapter(): CommonNavigatorAdapter {
        return TopCommonNavigatorAdapter(getTabList(), getViewPager2())
    }

    fun bindViewPager2() {
        val commonNavigator = CommonNavigator(mContext)
        commonNavigator.isAdjustMode = true

        val fragmentList = getFragmentList()

        val viewPagerAdapter = ViewPager2Adapter(mContext, fragmentList)

        binding.run {
            getViewPager2().run {
                adapter = viewPagerAdapter
                offscreenPageLimit = fragmentList.size()
                commonNavigator.adapter = getCommonNavigatorAdapter()
                getMagicIndicator().navigator = commonNavigator
                getMagicIndicator().bind(getViewPager2())
            }

        }


    }
}