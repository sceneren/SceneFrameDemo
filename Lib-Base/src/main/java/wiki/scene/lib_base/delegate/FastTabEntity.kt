package wiki.scene.lib_base.delegate

import android.text.TextUtils
import androidx.fragment.app.Fragment
import com.aries.ui.view.tab.listener.CustomTabEntity
import com.blankj.utilcode.util.StringUtils

class FastTabEntity(
    var mTitle: String,
    var mUnSelectedIcon: Int,
    var mSelectedIcon: Int,
    var mFragment: Fragment
) : CustomTabEntity {
    constructor(title: Int, unSelectedIcon: Int, selectedIcon: Int, fragment: Fragment) : this(
        StringUtils.getString(title), unSelectedIcon, selectedIcon, fragment
    )

    constructor(unSelectedIcon: Int, selectedIcon: Int, fragment: Fragment) : this(
        "",
        unSelectedIcon,
        selectedIcon,
        fragment
    )

    override fun getTabTitle(): String {
        return if (TextUtils.isEmpty(mTitle)) {
            ""
        } else mTitle
    }

    override fun getTabSelectedIcon(): Int {
        return mSelectedIcon
    }

    override fun getTabUnselectedIcon(): Int {
        return mUnSelectedIcon
    }
}