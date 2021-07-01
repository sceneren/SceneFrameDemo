package wiki.scene.lib_base.indicator.adapter

import android.content.Context
import android.view.LayoutInflater
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
import wiki.scene.lib_base.R
import wiki.scene.lib_base.databinding.LibBaseMainTabItemBinding
import wiki.scene.lib_base.ext.clicks
import wiki.scene.lib_base.indicator.entity.CustomBottomTabInfo

/**
 *
 * @Description:    自定义主页的tab
 * @Author:         scene
 * @CreateDate:     2021/7/1 11:30
 * @UpdateUser:
 * @UpdateDate:     2021/7/1 11:30
 * @UpdateRemark:
 * @Version:        1.0.0
 */
class BottomCommonNavigatorAdapter : CommonNavigatorAdapter {
    private var viewPager: ViewPager? = null
    private var viewPager2: ViewPager2? = null
    private var tabList: List<CustomBottomTabInfo>

    constructor(tabList: List<CustomBottomTabInfo>, viewPager: ViewPager) {
        this.tabList = tabList
        this.viewPager = viewPager
    }

    constructor(tabList: List<CustomBottomTabInfo>, viewPager2: ViewPager2) {
        this.tabList = tabList
        this.viewPager2 = viewPager2
    }

    override fun getCount(): Int {
        return tabList.size
    }

    override fun getTitleView(context: Context, index: Int): IPagerTitleView {
        val commonPagerTitleView = CommonPagerTitleView(context)
        val customTabItemView = LayoutInflater.from(context)
            .inflate(R.layout.lib_base_main_tab_item, commonPagerTitleView, false)

        val tabItemBinding = LibBaseMainTabItemBinding.bind(customTabItemView)
        tabItemBinding.titleText.text = tabList[index].title
        tabItemBinding.titleImg.setImageResource(tabList[index].normalTabImageRes)
        commonPagerTitleView.setContentView(customTabItemView)
        commonPagerTitleView.onPagerTitleChangeListener = object : OnPagerTitleChangeListener {
            override fun onSelected(index: Int, totalCount: Int) {
                tabItemBinding.titleText.setTextColor(tabList[index].selectTextColor)
                tabItemBinding.titleImg.setImageResource(tabList[index].selectTabImageRes)
            }

            override fun onDeselected(index: Int, totalCount: Int) {
                tabItemBinding.titleText.setTextColor(tabList[index].normalTextColor)
                tabItemBinding.titleImg.setImageResource(tabList[index].normalTabImageRes)
            }

            override fun onLeave(
                index: Int,
                totalCount: Int,
                leavePercent: Float,
                leftToRight: Boolean
            ) {

            }

            override fun onEnter(
                index: Int,
                totalCount: Int,
                enterPercent: Float,
                leftToRight: Boolean
            ) {
            }

        }

        commonPagerTitleView.clicks {
            viewPager?.currentItem = index
            viewPager2?.currentItem = index
        }
        return commonPagerTitleView
    }

    override fun getIndicator(context: Context): IPagerIndicator? {
        return null
    }
}