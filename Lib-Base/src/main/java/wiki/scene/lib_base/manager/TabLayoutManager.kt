package wiki.scene.lib_base.manager

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.aries.ui.view.tab.CommonTabLayout
import com.aries.ui.view.tab.SegmentTabLayout
import com.aries.ui.view.tab.SlidingTabLayout
import com.aries.ui.view.tab.listener.CustomTabEntity
import com.aries.ui.view.tab.listener.OnTabSelectListener
import java.util.*

class TabLayoutManager private constructor() {
    /**
     * 设置滑动 Tab SlidingTabLayout
     *
     * @param fragment
     * @param tabLayout
     * @param viewPager
     * @param tittles
     * @param fragments
     */
    fun setSlidingTabData(
        fragment: Fragment, tabLayout: SlidingTabLayout, viewPager: ViewPager,
        tittles: MutableList<String>, fragments: MutableList<Fragment>
    ) {
        setSlidingTabData(fragment, tabLayout, viewPager, tittles, fragments, null)
    }

    /**
     * 设置滑动 Tab SlidingTabLayout
     *
     * @param activity
     * @param tabLayout
     * @param viewPager
     * @param tittles
     * @param fragments
     */
    fun setSlidingTabData(
        activity: FragmentActivity?, tabLayout: SlidingTabLayout,
        viewPager: ViewPager, tittles: MutableList<String>, fragments: MutableList<Fragment>
    ) {
        setSlidingTabData(activity, tabLayout, viewPager, tittles, fragments, null)
    }

    /**
     * Fragment 里SlidingTabLayout 快速设置
     *
     * @param fragment
     * @param viewPager
     * @param tittles
     * @param fragments
     * @param tabLayout
     * @param listener
     */
    fun setSlidingTabData(
        fragment: Fragment,
        tabLayout: SlidingTabLayout,
        viewPager: ViewPager,
        tittles: MutableList<String>,
        fragments: MutableList<Fragment>,
        listener: OnTabSelectListener?
    ) {
        setViewPager(fragment, tabLayout, viewPager, tittles, fragments, listener)
        tabLayout.setViewPager(viewPager)
    }

    /**
     * FragmentActivity里SlidingTabLayout 快速设置
     *
     * @param activity
     * @param viewPager
     * @param tittles
     * @param fragments
     * @param tabLayout
     * @param MutableListener
     */
    fun setSlidingTabData(
        activity: FragmentActivity?,
        tabLayout: SlidingTabLayout,
        viewPager: ViewPager,
        tittles: MutableList<String>,
        fragments: MutableList<Fragment>,
        MutableListener: OnTabSelectListener?
    ) {
        setViewPager(activity, tabLayout, viewPager, tittles, fragments, MutableListener)
        tabLayout.setViewPager(viewPager)
    }

    fun setCommonTabData(
        fragment: Fragment, tabLayout: CommonTabLayout, viewPager: ViewPager,
        tabs: ArrayList<CustomTabEntity>, fragments: MutableList<Fragment>
    ) {
        setCommonTabData(fragment, tabLayout, viewPager, tabs, fragments, null)
    }

    fun setCommonTabData(
        activity: FragmentActivity, tabLayout: CommonTabLayout, viewPager: ViewPager,
        tabs: ArrayList<CustomTabEntity>, fragments: MutableList<Fragment>
    ) {
        setCommonTabData(activity, tabLayout, viewPager, tabs, fragments, null)
    }

    /**
     * 快速设置 Fragment CommonTabLayout
     *
     * @param fragment
     * @param viewPager
     * @param tabs
     * @param fragments
     * @param tabLayout
     * @param MutableListener
     */
    fun setCommonTabData(
        fragment: Fragment,
        tabLayout: CommonTabLayout,
        viewPager: ViewPager,
        tabs: ArrayList<CustomTabEntity>,
        fragments: MutableList<Fragment>,
        MutableListener: OnTabSelectListener?
    ) {
        setViewPager(fragment, tabLayout, viewPager, null, fragments, MutableListener)
        tabLayout.setTabData(tabs)
    }

    /**
     * 快速设置CommonTabLayout
     *
     * @param activity
     * @param viewPager
     * @param tabs
     * @param fragments
     * @param tabLayout
     * @param MutableListener
     */
    fun setCommonTabData(
        activity: FragmentActivity,
        tabLayout: CommonTabLayout,
        viewPager: ViewPager,
        tabs: ArrayList<CustomTabEntity>,
        fragments: MutableList<Fragment>,
        MutableListener: OnTabSelectListener?
    ) {
        setViewPager(activity, tabLayout, viewPager, null, fragments, MutableListener)
        tabLayout.setTabData(tabs)
    }

    fun setSegmentTabData(
        fragment: Fragment, tabLayout: SegmentTabLayout, viewPager: ViewPager,
        titles: Array<String>, fragments: MutableList<Fragment>
    ) {
        setSegmentTabData(fragment, tabLayout, viewPager, titles, fragments, null)
    }

    fun setSegmentTabData(
        activity: FragmentActivity, tabLayout: SegmentTabLayout, viewPager: ViewPager,
        titles: Array<String>, fragments: MutableList<Fragment>
    ) {
        setSegmentTabData(activity, tabLayout, viewPager, titles, fragments, null)
    }

    /**
     * 快速设置Fragment 里SegmentTabLayout
     *
     * @param fragment
     * @param tabLayout
     * @param viewPager
     * @param titles
     * @param fragments
     * @param MutableListener
     */
    fun setSegmentTabData(
        fragment: Fragment,
        tabLayout: SegmentTabLayout,
        viewPager: ViewPager,
        titles: Array<String>,
        fragments: MutableList<Fragment>,
        MutableListener: OnTabSelectListener?
    ) {
        setViewPager(
            fragment,
            tabLayout,
            viewPager,
            mutableListOf(*titles),
            fragments,
            MutableListener
        )
        tabLayout.setTabData(titles)
    }

    /**
     * 快速设置 FragmentActivity 里SegmentTabLayout
     *
     * @param activity
     * @param tabLayout
     * @param viewPager
     * @param titles 标签数组
     * @param fragments fragment 数组
     * @param MutableListener  tab切换监听回调
     */
    fun setSegmentTabData(
        activity: FragmentActivity,
        tabLayout: SegmentTabLayout,
        viewPager: ViewPager,
        titles: Array<String>,
        fragments: MutableList<Fragment>,
        MutableListener: OnTabSelectListener?
    ) {
        setViewPager(activity, tabLayout, viewPager, null, fragments, MutableListener)
        tabLayout.setTabData(titles)
    }

    /**
     * viewPager配合使用
     *
     * @param activity  FragmentActivity或Fragment
     * @param viewPager 装载 Fragment的容器
     * @param titles   标签数组
     * @param fragments 加载Fragment数组
     * @param MutableListener  tab切换回调
     */
    private fun setViewPager(
        activity: Any?,
        tabLayout: Any,
        viewPager: ViewPager,
        titles: MutableList<String>?,
        fragments: MutableList<Fragment>,
        MutableListener: OnTabSelectListener?
    ) {
        if (activity == null) {
            return
        }
        if (activity !is FragmentActivity
            && activity !is Fragment
        ) {
            return
        }
        if (tabLayout !is CommonTabLayout
            && tabLayout !is SlidingTabLayout
            && tabLayout !is SegmentTabLayout
        ) {
            return
        }
        viewPager.overScrollMode = View.OVER_SCROLL_NEVER
        viewPager.adapter = getFragmentAdapter(activity, titles, fragments)
        viewPager.offscreenPageLimit = fragments.size
        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                if (tabLayout is CommonTabLayout) {
                    tabLayout.currentTab = position
                } else if (tabLayout is SegmentTabLayout) {
                    tabLayout.currentTab = position
                }
                MutableListener?.onTabSelect(position)
            }
        })
        if (tabLayout is CommonTabLayout) {
            tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
                override fun onTabSelect(position: Int) {
                    viewPager.currentItem = position
                }

                override fun onTabReselect(position: Int) {
                    MutableListener?.onTabReselect(position)
                }
            })
        } else if (tabLayout is SegmentTabLayout) {
            tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
                override fun onTabSelect(position: Int) {
                    viewPager.currentItem = position
                }

                override fun onTabReselect(position: Int) {
                    MutableListener?.onTabReselect(position)
                }
            })
        }
    }

    /**
     * 快速设置适配器
     *
     * @param activity  Fragment或FragmentActivity
     * @param titles   标签列表
     * @param fragments Fragment列表
     * @return FragmentStatePagerAdapter适配器
     */
    private fun getFragmentAdapter(
        activity: Any, titles: MutableList<String>?,
        fragments: MutableList<Fragment>
    ): FragmentStatePagerAdapter? {
        var manager: FragmentManager? = null
        if (activity is FragmentActivity) {
            manager = activity.supportFragmentManager
        } else if (activity is Fragment) {
            manager = activity.childFragmentManager
        }
        return if (manager == null) {
            null
        } else object : FragmentStatePagerAdapter(manager) {
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int {
                return fragments.size
            }

            override fun getPageTitle(position: Int): CharSequence {
                if (titles == null) {
                    return ""
                }
                return if (position < titles.size) titles[position] else ""
            }
        }
    }

    companion object {
        @Volatile
        var instance: TabLayoutManager? = null
            get() {
                if (field == null) {
                    synchronized(TabLayoutManager::class.java) {
                        if (field == null) {
                            field = TabLayoutManager()
                        }
                    }
                }
                return field
            }
            private set
    }
}