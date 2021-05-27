package wiki.scene.lib_base.base_fg.fragmentvisibility

import com.trello.rxlifecycle2.components.support.RxFragment

open class RxVisibilityFragment : RxFragment(), IFragmentVisibility {

    // True if the fragment is visible to the user.
    private var mIsFragmentVisible = false

    // True if the fragment is visible to the user for the first time.
    private var mIsFragmentVisibleFirst = true

    override fun onResume() {
        super.onResume()

        determineFragmentVisible()
    }

    override fun onPause() {
        super.onPause()

        determineFragmentInvisible()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        if (hidden) {
            determineFragmentInvisible()
        } else {
            determineFragmentVisible()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if (isVisibleToUser) {
            determineFragmentVisible()
        } else {
            determineFragmentInvisible()
        }
    }

    override fun isVisibleToUser(): Boolean = mIsFragmentVisible

    private fun determineFragmentVisible() {
        val parent = parentFragment
        if (parent != null && parent is RxVisibilityFragment) {
            if (!parent.isVisibleToUser()) {
                return
            }
        }

        if (isResumed && !isHidden && userVisibleHint && !mIsFragmentVisible) {
            mIsFragmentVisible = true
            onVisible()
            if (mIsFragmentVisibleFirst) {
                mIsFragmentVisibleFirst = false
                onVisibleFirst()
            } else {
                onVisibleExceptFirst()
            }
            determineChildFragmentVisible()
        }
    }

    private fun determineFragmentInvisible() {
        if (mIsFragmentVisible) {
            mIsFragmentVisible = false
            onInvisible()
            determineChildFragmentInvisible()
        }
    }

    private fun determineChildFragmentVisible() {
        childFragmentManager.fragments.forEach {
            if (it is RxVisibilityFragment) {
                it.determineFragmentVisible()
            }
        }
    }

    private fun determineChildFragmentInvisible() {
        childFragmentManager.fragments.forEach {
            if (it is RxVisibilityFragment) {
                it.determineFragmentInvisible()
            }
        }
    }
}