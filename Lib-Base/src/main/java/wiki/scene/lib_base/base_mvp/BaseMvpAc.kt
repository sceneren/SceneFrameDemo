package wiki.scene.lib_base.base_mvp

import androidx.viewbinding.ViewBinding
import wiki.scene.lib_base.base_ac.BaseAc
import wiki.scene.lib_base.base_mvp.i.IBasePresenter

abstract class BaseMvpAc<VB : ViewBinding, P : IBasePresenter> : BaseAc<VB>() {
    open var mPresenter: P? = null

    abstract fun initPresenter()

    override fun beforeInitView() {
        initPresenter()
    }
}
