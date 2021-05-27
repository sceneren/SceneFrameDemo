package wiki.scene.lib_base.base_util

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commitNow
import androidx.lifecycle.Lifecycle
import wiki.scene.lib_base.base_fg.fragmentvisibility.RxVisibilityFragment

object FragmentContainerUtil {

    fun initFragmentInNewWay(
        fm: FragmentManager,
        id: Int,
        fragmentList: ArrayList<RxVisibilityFragment>
    ) {
        fm.commitNow {
            fragmentList.indices.forEach {
                val fragment = fragmentList[it]
                add(id, fragment)
                if (it == 0) {
                    setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
                } else {
                    hide(fragment)
                    setMaxLifecycle(fragment, Lifecycle.State.STARTED)
                }
            }
        }
    }

    fun showFragmentByPositionInNewWay(
        fm: FragmentManager,
        fragmentList: ArrayList<RxVisibilityFragment>,
        position: Int
    ) {
        val targetFragment = fragmentList[position]
        if (targetFragment.isResumed) {
            return
        }

        fm.commitNow {
            fragmentList.forEach { fragment ->
                if (fragment.isResumed) {
                    hide(fragment)
                    setMaxLifecycle(fragment, Lifecycle.State.STARTED)
                }
            }
            show(targetFragment)
            setMaxLifecycle(targetFragment, Lifecycle.State.RESUMED)
        }
    }
}