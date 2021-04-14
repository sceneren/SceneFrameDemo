package wiki.scene.lib_base.base_mvp

import androidx.viewbinding.ViewBinding
import wiki.scene.lib_base.base_fg.BaseRecyclerViewFg
import wiki.scene.lib_base.base_mvp.i.IBasePresenter

abstract class BaseMvpRecyclerViewFg<VB : ViewBinding, T, P : IBasePresenter> :
    BaseRecyclerViewFg<VB, T>() {
    open var mPresenter: P? = null

    abstract fun initPresenter()

    override fun beforeLoadData() {
        super.beforeLoadData()
        initPresenter()
    }

    override fun onDestroyView() {
        if (mPresenter != null) {
            mPresenter = null
        }
        super.onDestroyView()
    }
}