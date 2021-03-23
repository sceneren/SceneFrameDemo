package wiki.scene.lib_base.base_mvp.i

import androidx.lifecycle.LifecycleOwner

interface IBasePresenter {
    /**
     * 做一些初始化操作
     */
    fun onStart()

    /**
     * 销毁
     */
    fun onDestroy()

    fun bindToLifeCycle(): LifecycleOwner?
}