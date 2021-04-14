package wiki.scene.lib_base.base_mvp

import androidx.viewbinding.ViewBinding
import wiki.scene.lib_base.base_ac.BaseRecyclerViewAc
import wiki.scene.lib_base.base_mvp.i.IBasePresenter
import wiki.scene.lib_base.base_mvp.i.IRecyclerViewBaseView

abstract class BaseMvpRecyclerViewAc<VB : ViewBinding, T, P : IBasePresenter> :
    BaseRecyclerViewAc<VB, T>() {
    open var mPresenter: P? = null

    abstract fun initPresenter()

    override fun beforeInitView() {
        initPresenter()
    }

    override fun onDestroy() {
        mPresenter?.onDestroy()
        super.onDestroy()
    }
}