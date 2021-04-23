package wiki.scene.lib_base.impl

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.aries.ui.view.tab.CommonTabLayout
import com.aries.ui.view.tab.listener.OnTabSelectListener
import wiki.scene.lib_base.R
import wiki.scene.lib_base.delegate.FastTabEntity

interface IFastMainView : OnTabSelectListener {
    /**
     * 控制主界面Fragment是否可滑动切换
     *
     * @return true 可滑动切换(配合ViewPager)
     */
    fun isSwipeEnable(): Boolean {
        return false
    }

    /**
     * 承载主界面Fragment的ViewGroup id 一般为FrameLayout
     *
     * @return viewId
     */
    fun getContainerViewId(): Int {
        return R.id.fLayout_containerFastMain
    }

    /**
     * 用于添加Tab属性(文字-图标)
     *
     * @return 主页tab数组
     */
    fun getTabList(): MutableList<FastTabEntity>

    /**
     * 获取onCreate 携带参数
     *
     * @return
     */
    fun getSavedInstanceState(): Bundle?

    /**
     * 返回 CommonTabLayout  对象用于自定义设置
     *
     * @param tabLayout CommonTabLayout 对象用于单独属性调节
     */
    fun setTabLayout(tabLayout: CommonTabLayout)

    /**
     * 设置ViewPager属性
     *
     * @param mViewPager ViewPager属性控制
     */
    fun setViewPager(mViewPager: ViewPager) {}

    /**
     * tab首次选中
     *
     * @param position
     */
    override fun onTabSelect(position: Int) {}

    /**
     * tab选中状态再点击
     *
     * @param position
     */
    override fun onTabReselect(position: Int) {}
}