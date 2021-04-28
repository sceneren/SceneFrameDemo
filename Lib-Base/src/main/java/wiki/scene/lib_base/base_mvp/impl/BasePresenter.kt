package wiki.scene.lib_base.base_mvp.impl

import com.trello.rxlifecycle2.LifecycleTransformer
import wiki.scene.lib_base.base_mvp.i.IBaseModel
import wiki.scene.lib_base.base_mvp.i.IBasePresenter
import wiki.scene.lib_base.base_mvp.i.IBaseView

abstract class BasePresenter<VM : IBaseModel, V : IBaseView>(
    protected val mBaseModel: VM,
    protected val mBaseView: V
) : IBasePresenter {

    fun <T> getLifecycle(): LifecycleTransformer<T> {
        return mBaseView.getLifecycleTransformer()
    }
}