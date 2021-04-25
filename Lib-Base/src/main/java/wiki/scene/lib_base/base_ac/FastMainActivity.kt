package wiki.scene.lib_base.base_ac

import android.os.Bundle
import com.aries.ui.view.tab.CommonTabLayout
import wiki.scene.lib_base.databinding.LibBaseFastActMainBinding
import wiki.scene.lib_base.delegate.FastMainTabDelegate
import wiki.scene.lib_base.impl.IFastMainView

abstract class FastMainActivity : BaseAc<LibBaseFastActMainBinding>(), IFastMainView {
    var mFastMainTabDelegate: FastMainTabDelegate? = null

    override fun onSaveInstanceState(outState: Bundle) {
        mFastMainTabDelegate?.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun beforeInitView() {
        super.beforeInitView()
        mFastMainTabDelegate = FastMainTabDelegate(binding.root, this, this)
    }

    override fun getSavedInstanceState(): Bundle? {
        return mSavedInstanceState
    }

    override fun isDoubleClickExit(): Boolean {
        return true
    }

    override fun onDestroy() {
        mFastMainTabDelegate?.onDestroy()
        super.onDestroy()
    }

    override fun setTabLayout(tabLayout: CommonTabLayout) {

    }

    override fun canSwipeBack(): Boolean {
        return false
    }

}