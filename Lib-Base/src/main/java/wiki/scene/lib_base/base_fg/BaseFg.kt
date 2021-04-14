package wiki.scene.lib_base.base_fg

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.aries.ui.view.title.TitleBarView
import com.dylanc.viewbinding.base.inflateBindingWithGeneric
import com.hjq.toast.ToastUtils
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import io.reactivex.Observable
import wiki.scene.lib_base.base_mvp.i.IBaseView
import wiki.scene.lib_base.databinding.LibBaseTitleBarViewBinding
import wiki.scene.lib_base.ext.changeIOThread
import wiki.scene.lib_base.loadsir.EmptyCallback
import wiki.scene.lib_base.loadsir.ErrorCallback
import wiki.scene.lib_base.loadsir.LoadingCallback
import java.util.concurrent.TimeUnit

abstract class BaseFg<VB : ViewBinding> : Fragment(), IBaseView {
    protected open lateinit var mContext: Context
    protected open lateinit var mActivity: AppCompatActivity
    protected open var mIsFirstShow = false
    protected open var mIsViewLoaded = false
    protected open var mIsVisibleChanged = false
    private val mIsInViewPager = false

    protected open var mSavedInstanceState: Bundle? = null

    private lateinit var rootView: View
    private lateinit var parent: ViewGroup
    private var loadService: LoadService<*>? = null

    private var _binding: VB? = null
    open val binding: VB get() = _binding!!

    open var titleBarBinding: LibBaseTitleBarViewBinding? = null


    protected open fun isSingleFragment(): Boolean {
        val manager = parentFragmentManager
        val size = manager.fragments.size
        return size <= 1
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = (context as AppCompatActivity)
        mIsFirstShow = true
        ARouter.getInstance().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mSavedInstanceState = savedInstanceState
        beforeCreateView()
        _binding = inflateBindingWithGeneric(layoutInflater)
        rootView = binding.root
        if (rootView.parent != null) {
            parent = rootView.parent as ViewGroup
            parent.removeView(rootView)
        }

        if (isSingleFragment() && !mIsVisibleChanged) {
            if (userVisibleHint || isVisible || !isHidden) {
                onVisibleChanged(true)
            }
        }

        return rootView
    }

    open fun beforeCreateView() {

    }

    protected open fun beforeInitView(savedInstanceState: Bundle?) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mIsViewLoaded = true
        beforeInitView(savedInstanceState)
        if (hasTitleBarView()) {
            titleBarBinding = LibBaseTitleBarViewBinding.bind(binding.root)
            initToolBarView(titleBarBinding!!.libBaseTvTitleBar)
        }
        initViews()
    }

    abstract fun loadData()

    open fun initViews() {}

    override fun showLoading() {
        if (loadService == null) {
            loadService = if (injectLoadServiceView() == null) {
                LoadSir.getDefault().register(this) { onRetryBtnClick() }
            } else {
                LoadSir.getDefault().register(injectLoadServiceView()) { onRetryBtnClick() }
            }
        }
        loadService!!.showCallback(LoadingCallback::class.java)
    }

    override fun showEmpty() {
        if (loadService == null) {
            loadService = LoadSir.getDefault().register(this) { onRetryBtnClick() }
        }
        loadService!!.showCallback(EmptyCallback::class.java)
    }

    override fun showSuccess() {
        if (loadService == null) {
            loadService = LoadSir.getDefault().register(this) { onRetryBtnClick() }
        }
        loadService!!.showSuccess()
    }

    override fun showError() {
        if (loadService == null) {
            loadService = LoadSir.getDefault().register(this) { onRetryBtnClick() }
        }
        loadService!!.showCallback(ErrorCallback::class.java)
    }

    override fun onRetryBtnClick() {}

    override fun getView(): View? {
        return rootView
    }

    override fun onResume() {
        super.onResume()
        if (isAdded && isVisibleToUser(this)) {
            onVisibleChanged(true)
        }
    }

    private fun isVisibleToUser(fragment: BaseFg<out VB>): Boolean {
        if (fragment.parentFragment != null) {
            return isVisibleToUser(fragment.parentFragment as BaseFg<VB>) && if (fragment.isInViewPager()) fragment.userVisibleHint else fragment.isVisible
        }
        return if (fragment.isInViewPager()) fragment.userVisibleHint else fragment.isVisible
    }

    @SuppressLint("CheckResult")
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!mIsViewLoaded) {
            Observable.just(10)
                .delay(10, TimeUnit.MILLISECONDS)
                .changeIOThread()
                .subscribe {
                    onHiddenChanged(hidden)
                }
        } else {
            onVisibleChanged(!hidden)
        }
    }

    open fun isInViewPager(): Boolean {
        return mIsInViewPager
    }

    @SuppressLint("CheckResult")
    protected open fun onVisibleChanged(isVisibleToUser: Boolean) {
        mIsVisibleChanged = true
        if (isVisibleToUser) {
            //避免因视图未加载子类刷新UI抛出异常
            if (!mIsViewLoaded) {
                Observable.just(10)
                    .delay(10, TimeUnit.MILLISECONDS)
                    .changeIOThread()
                    .subscribe {
                        onVisibleChanged(true)
                    }
            } else {
                fastLazyLoad()
            }
        }
    }

    private fun fastLazyLoad() {
        if (mIsFirstShow && mIsViewLoaded) {
            mIsFirstShow = false
            beforeLoadData()
            loadData()
        }
    }

    open fun beforeLoadData() {}


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * 如果界面引用titleBarView的话要重写此方法返回true
     */
    open fun hasTitleBarView(): Boolean {
        return false
    }

    open fun initToolBarView(titleBarView: TitleBarView) {

    }

    override fun findActivity(): AppCompatActivity {
        return this.mActivity
    }

    override fun findFragment(): Fragment? {
        return this
    }

    override fun findContext(): Context {
        return mContext
    }

    override fun showToast(msg: String?) {
        msg?.let {
            ToastUtils.show(msg)
        }
    }

    override fun showToast(stringResId: Int) {
        if (stringResId != 0) {
            ToastUtils.show(stringResId)
        }
    }

    open fun injectLoadServiceView(): View? {
        return null
    }

}