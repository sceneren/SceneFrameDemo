package wiki.scene.lib_base.indicator.adapter

import android.content.Context
import android.graphics.Color
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.ColorUtils
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import wiki.scene.lib_base.R
import wiki.scene.lib_base.ext.clicks

/**
 *
 * @Description:   自定义上面的 CommonNavigatorAdapter
 * @Author:         scene
 * @CreateDate:     2021/7/9 16:14
 * @UpdateUser:
 * @UpdateDate:     2021/7/9 16:14
 * @UpdateRemark:
 * @Version:        1.0.0
 */
class TopCommonNavigatorAdapter : CommonNavigatorAdapter {
    private var viewPager: ViewPager? = null
    private var viewPager2: ViewPager2? = null
    private var tabList: List<String>

    constructor(tabList: List<String>, viewPager: ViewPager) {
        this.tabList = tabList
        this.viewPager = viewPager
    }

    constructor(tabList: List<String>, viewPager2: ViewPager2) {
        this.tabList = tabList
        this.viewPager2 = viewPager2
    }

    override fun getCount(): Int {
        return tabList.size
    }

    override fun getTitleView(context: Context, index: Int): IPagerTitleView {
        val commonPagerTitleView = ColorTransitionPagerTitleView(context)
        commonPagerTitleView.normalColor = Color.GRAY
        commonPagerTitleView.selectedColor = ColorUtils.getColor(R.color.colorAccent)
        commonPagerTitleView.text = tabList[index]
        commonPagerTitleView.textSize = 15F
        commonPagerTitleView.clicks {
            viewPager?.currentItem = index
            viewPager2?.currentItem = index
        }

        return commonPagerTitleView
    }

    override fun getIndicator(context: Context): IPagerIndicator {
        val linePagerIndicator = LinePagerIndicator(context)
        linePagerIndicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
        linePagerIndicator.setColors(ColorUtils.getColor(R.color.colorAccent))
        return linePagerIndicator
    }
}