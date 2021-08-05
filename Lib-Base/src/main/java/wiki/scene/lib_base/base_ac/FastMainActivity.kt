package wiki.scene.lib_base.base_ac

import android.util.SparseArray
import androidx.core.util.isEmpty
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ColorUtils
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import wiki.scene.lib_base.R
import wiki.scene.lib_base.databinding.LibBaseFastActMainBinding
import wiki.scene.lib_base.indicator.adapter.BottomCommonNavigatorAdapter
import wiki.scene.lib_base.indicator.adapter.ViewPager2Adapter
import wiki.scene.lib_base.indicator.entity.CustomBottomTabInfo
import wiki.scene.lib_base.indicator.ext.bind

abstract class FastMainActivity : BaseAc<LibBaseFastActMainBinding>() {
    override fun initViews() {
        super.initViews()
        if (getFragmentList().isEmpty() || getTabList().isEmpty() || getFragmentList().size() != getTabList().size) {
            throw Exception("tabList or fragmentList error!")
        }
        binding.dividerView.setBackgroundColor(getDividerColor())
        val layoutParams = binding.dividerView.layoutParams
        layoutParams.height = getDividerHeight()

        val viewPager2Adapter = ViewPager2Adapter(this, getFragmentList())
        binding.viewPager2.run {
            adapter = viewPager2Adapter
            isUserInputEnabled = false
            offscreenPageLimit = getFragmentList().size()
        }

        val commonNavigator = CommonNavigator(this).apply {
            isAdjustMode = true
            adapter = BottomCommonNavigatorAdapter(getTabList(), binding.viewPager2)
        }

        binding.magicIndicator.run {
            navigator = commonNavigator
            bind(binding.viewPager2)
        }
    }

    override fun hasTitleBarView(): Boolean {
        return false
    }

    override fun isDoubleClickExit(): Boolean {
        return true
    }

    abstract fun getFragmentList(): SparseArray<Fragment>

    abstract fun getTabList(): List<CustomBottomTabInfo>

    open fun getDividerColor(): Int {
        return ColorUtils.getColor(R.color.lib_base_tab_divider_color)
    }

    open fun getDividerHeight(): Int {
        return 0
    }

    override fun canSwipeBack(): Boolean {
        return false
    }
}