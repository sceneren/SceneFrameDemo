package wiki.scene.lib_base.base_mvp.impl

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import wiki.scene.lib_base.base_mvp.i.IBaseModel
import wiki.scene.lib_base.base_mvp.i.IBasePresenter
import wiki.scene.lib_base.base_mvp.i.IBaseView

/**
 * @author chenke
 * @create 2020/6/30
 * @Describe
 */
open class BasePresenter<M : IBaseModel, V : IBaseView>(protected val mBaseModel: M,protected val mBaseView: V) :
    IBasePresenter, LifecycleObserver {
    var mCompositeDisposable: CompositeDisposable? = null

    init {
        onStart()
    }

    final override fun onStart() {
        //将 LifecycleObserver 注册给 LifecycleOwner 后 @OnLifecycleEvent 才可以正常使用
        if (mBaseView is LifecycleObserver) {
            //activity ，fragment绑定自身生命周期
            (mBaseView as LifecycleOwner).lifecycle.addObserver(this)
            if (mBaseModel is LifecycleObserver) {
                //activity ，fragment绑定model数据层的生命周期,当Activity销毁时，数据层感知其生命周期
                (mBaseView as LifecycleOwner).lifecycle
                    .addObserver(mBaseModel as LifecycleObserver)
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this)
    }

    fun addDispose(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable?.add(disposable) //将所有 Disposable 放入容器集中处理
    }

    /**
     * 停止集合中正在执行的 RxJava 任务
     */
    fun unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable?.clear() //保证 Activity 结束时取消所有正在执行的订阅
        }
    }

    override fun onDestroy() {
        unDispose()
        mBaseModel.onDestroy()
    }

    override fun bindToLifeCycle(): LifecycleOwner {
        return if (mBaseView.findFragment() == null) {
            mBaseView.findActivity()
        } else {
            mBaseView.findFragment()!!.viewLifecycleOwner
        }

    }
}