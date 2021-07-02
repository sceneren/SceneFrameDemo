package wiki.scene.lib_base.base_fg

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.dylanc.viewbinding.base.inflateBindingWithGeneric
import com.gyf.immersionbar.ImmersionBar
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.hjq.toast.ToastUtils
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.subjects.BehaviorSubject
import org.greenrobot.eventbus.EventBus
import wiki.scene.lib_base.base_fg.fragmentvisibility.RxVisibilityFragment
import wiki.scene.lib_base.base_mvp.i.IBaseView
import wiki.scene.lib_base.databinding.LibBaseTitleBarViewBinding
import wiki.scene.lib_base.loadsir.EmptyCallback
import wiki.scene.lib_base.loadsir.ErrorCallback
import wiki.scene.lib_base.loadsir.LoadingCallback

abstract class BaseFg<VB : ViewBinding> : RxVisibilityFragment(), IBaseView {
    private val mTag = javaClass.simpleName

    private val lifecycleSubject = BehaviorSubject.create<FragmentEvent>()
    protected open lateinit var mContext: Context
    protected open lateinit var mActivity: AppCompatActivity

    protected open var mSavedInstanceState: Bundle? = null

    private lateinit var rootView: View
    private lateinit var parent: ViewGroup
    private var loadService: LoadService<*>? = null

    private var _binding: VB? = null
    open val binding: VB get() = _binding!!

    private var isDarkMode = true
    open var titleBarBinding: LibBaseTitleBarViewBinding? = null


    protected open fun isSingleFragment(): Boolean {
        val manager = parentFragmentManager
        val size = manager.fragments.size
        return size <= 1
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = requireActivity() as AppCompatActivity
        mContext = requireContext()
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

        return rootView
    }

    open fun allowEventBus(): Boolean {
        return false
    }

    open fun beforeCreateView() {

    }

    protected open fun beforeInitView() {

    }

    override fun onVisibleFirst() {
        super.onVisibleFirst()
        if (allowEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        beforeLoadData()
        loadData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        beforeInitView()
        if (hasTitleBarView()) {
            titleBarBinding = LibBaseTitleBarViewBinding.bind(binding.root)
            initToolBarView(titleBarBinding!!.libBaseTvTitleBar)
        }
        initImmersionBar()
        initViews()
        initListener()
    }

    private fun initImmersionBar() {
        if (hasTitleBarView()) {
            ImmersionBar.with(this)
                .titleBar(titleBarBinding!!.libBaseTvTitleBar)
                .statusBarDarkFont(isDarkMode)
                .navigationBarDarkIcon(isDarkMode)
                .navigationBarColorInt(Color.WHITE)
                .keyboardEnable(true)
                .init()
        } else {
            ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(isDarkMode)
                .navigationBarDarkIcon(isDarkMode)
                .navigationBarColorInt(Color.WHITE)
                .keyboardEnable(true)
                .init()
        }
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

    open fun beforeLoadData() {}


    override fun onDestroyView() {
        if (allowEventBus()) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this)
            }
        }
        super.onDestroyView()
        _binding = null
    }

    /**
     * 如果界面引用titleBarView的话要重写此方法返回true
     */
    open fun hasTitleBarView(): Boolean {
        return false
    }

    open fun initToolBarView(titleBarView: TitleBar) {
        titleBarView.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(view: View?) {
                onTitleLeftClick()
            }

            override fun onTitleClick(view: View?) {
                onTitleCenterClick()
            }

            override fun onRightClick(view: View?) {
                onTitleRightClick()
            }

        })
    }

    open fun onTitleLeftClick() {

    }

    open fun onTitleCenterClick() {

    }

    open fun onTitleRightClick() {

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

    override fun <T> getLifecycleTransformer(): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycle(), FragmentEvent.DESTROY_VIEW)
    }

    open fun isDarkMode(isDarkMode: Boolean) {
        this.isDarkMode = isDarkMode
    }

    open fun initListener() {

    }
}