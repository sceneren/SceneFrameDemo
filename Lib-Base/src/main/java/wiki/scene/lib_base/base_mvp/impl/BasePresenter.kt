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
class BasePresenter<M : IBaseModel, V : IBaseView> : IBasePresenter, LifecycleObserver {
    protected var mCompositeDisposable: CompositeDisposable? = null
    protected var mBaseModel: M? = null
    protected var mRootView: V? = null

    /**
     * 如果当前页面同时需要 Model 层和 View 层,则使用此构造函数(默认)
     *
     * @param model
     * @param rootView
     */
    constructor(model: M, rootView: V) {
        mBaseModel = model
        mRootView = rootView
        onStart()
    }

    /**
     * 如果当前页面不需要操作数据,只需要 View 层,则使用此构造函数
     *
     * @param rootView
     */
    constructor(rootView: V) {
        mRootView = rootView
        onStart()
    }

    override fun onStart() {
        //将 LifecycleObserver 注册给 LifecycleOwner 后 @OnLifecycleEvent 才可以正常使用
        if (mRootView != null && mRootView is LifecycleObserver) {
            //activity ，fragment绑定自身生命周期
            (mRootView as LifecycleOwner).lifecycle.addObserver(this)
            if (mBaseModel != null && mBaseModel is LifecycleObserver) {
                //activity ，fragment绑定model数据层的生命周期,当Activity销毁时，数据层感知其生命周期
                (mRootView as LifecycleOwner).lifecycle
                    .addObserver(mBaseModel as LifecycleObserver)
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        /**
         * 注意, 如果在这里调用了 [.onDestroy] 方法, 会出现某些地方引用 `mModel` 或 `mRootView` 为 null 的情况
         * 比如在 [RxLifecycle] 终止 [Observable] 时, 在 [io.reactivex.Observable.doFinally] 中却引用了 `mRootView` 做一些释放资源的操作, 此时会空指针
         * 或者如果你声明了多个 @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY) 时在其他 @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
         * 中引用了 `mModel` 或 `mRootView` 也可能会出现此情况
         */
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
        mBaseModel?.onDestroy()
        mBaseModel = null
        mRootView = null
    }

    override fun bindToLifeCycle(): LifecycleOwner? {
        mRootView?.let {
            return if (it.findFragment() != null) {
                it.findFragment()!!.viewLifecycleOwner
            } else {
                it.findActivity()
            }
        }

        return null
    }
}