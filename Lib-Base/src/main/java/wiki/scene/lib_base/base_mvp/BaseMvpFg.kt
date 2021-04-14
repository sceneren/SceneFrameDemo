package wiki.scene.lib_base.base_mvp

import androidx.viewbinding.ViewBinding
import wiki.scene.lib_base.base_fg.BaseFg
import wiki.scene.lib_base.base_mvp.i.IBasePresenter

abstract class BaseMvpFg<VB : ViewBinding, P : IBasePresenter> : BaseFg<VB>() {
    open var mPresenter: P? = null

    abstract fun initPresenter()

    override fun beforeCreateView() {
        super.beforeCreateView()
        initPresenter()
    }

    override fun onDestroyView() {
        if (mPresenter != null) {
            mPresenter = null
        }
        super.onDestroyView()
    }
}