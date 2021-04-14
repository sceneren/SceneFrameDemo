package wiki.scene.lib_base.delegate

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.aries.ui.util.FindViewUtil
import com.aries.ui.view.tab.CommonTabLayout
import com.aries.ui.view.tab.listener.CustomTabEntity
import com.blankj.utilcode.util.LogUtils
import wiki.scene.lib_base.R
import wiki.scene.lib_base.entity.FastTabEntity
import wiki.scene.lib_base.impl.IFastMainView
import java.util.*

class FastMainTabDelegate {
    private var mContext: Context? = null
    private var mObject: Any? = null
    private var mFragmentManager: FragmentManager? = null
    private var mIFastMainView: IFastMainView? = null
    var mTabLayout: CommonTabLayout? = null
    var mListFastTab: MutableList<FastTabEntity> = mutableListOf()
    private var mTabEntities: ArrayList<CustomTabEntity> = arrayListOf()
    private var mSavedInstanceState: Bundle? = null
    private var mSelectedPosition = 0

    constructor(rootView: View?, activity: FragmentActivity?, iFastMainView: IFastMainView?) {
        if (iFastMainView == null || rootView == null || activity == null) {
            return
        }
        mContext = activity
        mObject = activity
        mIFastMainView = iFastMainView
        mFragmentManager = activity.supportFragmentManager
        mSavedInstanceState = iFastMainView.getSavedInstanceState()
        getTabLayout(rootView)
        initTabLayout()
    }

    constructor(rootView: View?, activity: Fragment?, iFastMainView: IFastMainView?) {
        if (iFastMainView == null || rootView == null || activity == null) {
            return
        }
        mContext = activity.context
        mObject = activity
        mIFastMainView = iFastMainView
        mFragmentManager = activity.childFragmentManager
        mSavedInstanceState = iFastMainView.getSavedInstanceState()
        getTabLayout(rootView)
        initTabLayout()
    }

    private fun initTabLayout() {
        if (mTabLayout == null) {
            return
        }
        mListFastTab = ArrayList()
        saveState
        //本地缓存及接口都没有获取到
        if (mListFastTab.size == 0) {
            return
        }
        LogUtils.i(
            "initTabLayout",
            "position:" + mSelectedPosition + ";getCurrentTab:" + mTabLayout!!.currentTab
        )
        mTabLayout!!.setBackgroundResource(R.color.lib_base_colorTabBackground)
        mTabLayout!!.delegate
            .setTextSelectColor(
                ContextCompat.getColor(
                    mContext!!,
                    R.color.lib_base_colorTabTextSelect
                )
            )
            .setTextUnSelectColor(
                ContextCompat.getColor(
                    mContext!!,
                    R.color.lib_base_colorTabTextUnSelect
                )
            )
            .setUnderlineColor(
                ContextCompat.getColor(
                    mContext!!,
                    R.color.lib_base_colorTabUnderline
                )
            )
            .setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                mContext!!.resources.getDimensionPixelSize(R.dimen.lib_base_dp_tab_text_size)
                    .toFloat()
            )
            .setTextSelectSize(
                TypedValue.COMPLEX_UNIT_PX,
                mContext!!.resources.getDimensionPixelSize(R.dimen.lib_base_dp_tab_text_size)
                    .toFloat()
            )
            .setUnderlineGravity(Gravity.TOP)
            .setUnderlineHeight(mContext!!.resources.getDimensionPixelSize(R.dimen.lib_base_dp_tab_underline))
            .setIconMargin(mContext!!.resources.getDimensionPixelSize(R.dimen.lib_base_dp_tab_margin))
            .setIconWidth(mContext!!.resources.getDimensionPixelSize(R.dimen.lib_base_dp_tab_icon))
            .setIconHeight(mContext!!.resources.getDimensionPixelSize(R.dimen.lib_base_dp_tab_icon)).indicatorHeight =
            0
        val params = mTabLayout!!.layoutParams
        if (params != null) {
            params.height =
                mContext!!.resources.getDimensionPixelSize(R.dimen.lib_base_dp_tab_height)
        }
        val fragments = ArrayList<Fragment>()
        for (i in mListFastTab.indices) {
            val entity = mListFastTab[i]
            fragments.add(entity.mFragment)
            mTabEntities.add(entity)
        }

        if (mObject is FragmentActivity) {
            mTabLayout!!.setTabData(
                mTabEntities,
                mObject as FragmentActivity,
                mIFastMainView!!.getContainerViewId(),
                fragments
            )
            mTabLayout!!.setOnTabSelectListener(mIFastMainView)
        } else if (mObject is Fragment) {
            mTabLayout!!.setTabData(
                mTabEntities,
                (mObject as Fragment).activity,
                mIFastMainView!!.getContainerViewId(),
                fragments
            )
            mTabLayout!!.setOnTabSelectListener(mIFastMainView)
        }

        mIFastMainView!!.setTabLayout(mTabLayout!!)
        mTabLayout!!.currentTab = mSelectedPosition
    }

    /**
     * 获取布局里的CommonTabLayout
     *
     * @param rootView
     * @return
     */
    private fun getTabLayout(rootView: View) {
        mTabLayout = rootView.findViewById(R.id.tabLayout_commonFastLib)
        if (mTabLayout == null) {
            mTabLayout = FindViewUtil.getTargetView(rootView, CommonTabLayout::class.java)
        }
    }

    /**
     * 保存Fragment数据
     *
     * @param outState
     */
    fun onSaveInstanceState(outState: Bundle) {
        if (mTabLayout != null && mFragmentManager != null) {
            outState.putInt(SAVED_INSTANCE_STATE_FRAGMENT_NUMBER, mListFastTab.size)
            outState.putInt(SAVED_INSTANCE_STATE_CURRENT_TAB, mTabLayout!!.currentTab)
            val listFastTab: MutableList<FastTabEntity> = mListFastTab
            for (i in listFastTab.indices) {
                val item = listFastTab[i]
                outState.putInt(
                    SAVED_INSTANCE_STATE_KEY_UN_SELECTED_ICON + i,
                    item.mUnSelectedIcon
                )
                outState.putInt(SAVED_INSTANCE_STATE_KEY_SELECTED_ICON + i, item.mSelectedIcon)
                outState.putString(SAVED_INSTANCE_STATE_KEY_TITLE + i, item.mTitle)
                mFragmentManager!!.putFragment(
                    outState,
                    SAVED_INSTANCE_STATE_KEY_FRAGMENT + i,
                    item.mFragment
                )




            }
        }
    }//先获取数量
    //没有获取到
//从本地缓存获取
    /**
     * 获取本地存储信息
     */
    private val saveState: Unit
        get() {
            //从本地缓存获取
            if (mSavedInstanceState != null) {
                //先获取数量
                val size = mSavedInstanceState!!.getInt(SAVED_INSTANCE_STATE_FRAGMENT_NUMBER)
                if (size > 0) {
                    for (i in 0 until size) {
                        val fragment = mFragmentManager!!.getFragment(
                            mSavedInstanceState!!, SAVED_INSTANCE_STATE_KEY_FRAGMENT + i
                        )
                        val title =
                            mSavedInstanceState!!.getString(SAVED_INSTANCE_STATE_KEY_TITLE + i)
                        val selectedIcon = mSavedInstanceState!!.getInt(
                            SAVED_INSTANCE_STATE_KEY_SELECTED_ICON + i
                        )
                        val unSelectedIcon = mSavedInstanceState!!.getInt(
                            SAVED_INSTANCE_STATE_KEY_UN_SELECTED_ICON + i
                        )
                        mListFastTab.add(
                            FastTabEntity(
                                title!!,
                                unSelectedIcon,
                                selectedIcon,
                                fragment!!
                            )
                        )
                    }
                }
                mSelectedPosition = mSavedInstanceState!!.getInt(SAVED_INSTANCE_STATE_CURRENT_TAB)
            }
            //没有获取到
            if (mListFastTab.size == 0) {
                mListFastTab = mIFastMainView!!.getTabList()
            }
        }

    /**
     * 与Activity 及Fragment onDestroy 及时解绑释放避免内存泄露
     */
    fun onDestroy() {
        mContext = null
        mObject = null
        mFragmentManager = null
        mIFastMainView = null
        mTabLayout = null
        mListFastTab.clear()
        mTabEntities.clear()
        if (mSavedInstanceState != null) {
            mSavedInstanceState!!.clear()
            mSavedInstanceState = null
        }
        LogUtils.i("FastMainTabDelegate", "onDestroy")
    }

    companion object {
        const val SAVED_INSTANCE_STATE_CURRENT_TAB = "saved_instance_state_current_tab"

        /**
         * 存放历史主页Tab数量
         */
        const val SAVED_INSTANCE_STATE_FRAGMENT_NUMBER = "saved_instance_state_fragment_number"

        /**
         * 获取历史主页Tab数量Key --
         */
        const val SAVED_INSTANCE_STATE_KEY_FRAGMENT = "saved_instance_state_key_fragment"
        const val SAVED_INSTANCE_STATE_KEY_TITLE = "saved_instance_state_key_title"
        const val SAVED_INSTANCE_STATE_KEY_SELECTED_ICON = "saved_instance_state_key_selected_icon"
        const val SAVED_INSTANCE_STATE_KEY_UN_SELECTED_ICON =
            "saved_instance_state_key_un_selected_icon"
    }
}