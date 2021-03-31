package wiki.scene.lib_base.base_ac

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.aries.ui.util.StatusBarUtil
import com.aries.ui.view.title.TitleBarView
import com.dylanc.viewbinding.base.inflateBindingWithGeneric
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import wiki.scene.lib_base.R
import wiki.scene.lib_base.base_util.DoubleClickExitDetector
import wiki.scene.lib_base.base_util.InputTools
import wiki.scene.lib_base.base_util.LanguageUtil
import wiki.scene.lib_base.databinding.LibBaseTitleBarViewBinding
import wiki.scene.lib_base.impl.IAcView
import wiki.scene.lib_base.impl.INetView
import wiki.scene.lib_base.loadsir.EmptyCallback
import wiki.scene.lib_base.loadsir.ErrorCallback
import wiki.scene.lib_base.loadsir.LoadingCallback
import wiki.scene.lib_base.widget.slideback.SlideBack

/**
 * Created by zlx on 2017/6/23.
 */
abstract class BaseAc<VB : ViewBinding> : AppCompatActivity(), INetView, IAcView {
    lateinit var binding: VB
    open var titleBarBinding: LibBaseTitleBarViewBinding? = null

    private var loadService: LoadService<*>? = null
    protected lateinit var mContext: AppCompatActivity

    private var isDarkMode = false
    var mSavedInstanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        beforeOnCreate()
        super.onCreate(savedInstanceState)
        mSavedInstanceState = savedInstanceState
        initStatusBarMode()

        ARouter.getInstance().inject(this)
        mContext = this
        afterOnCreate()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT //竖屏
        setTheme(mTheme)
        setSuspension()
        //绑定viewBinding
        binding = this.inflateBindingWithGeneric(layoutInflater)

        setContentView(binding.root)

        if (hasTitleBarView()) {
            titleBarBinding = LibBaseTitleBarViewBinding.bind(binding.root)
            initToolBarView(titleBarBinding!!.libBaseTvTitleBar)
        }

        initEvents()

        beforeInitView()

        initViews()

        afterInitViews()

        loadData()

        doubleClickExitDetector = DoubleClickExitDetector(this, "再按一次退出", 2000)

        if (canSwipeBack()) {
            //开启滑动返回
            SlideBack.create()
                .attachToActivity(this)
        }
    }

    /**
     * 如果界面没有引用titleBarView的话要重写此方法返回false
     */
    open fun hasTitleBarView(): Boolean {
        return true
    }

    open fun hasTitleBarBack(): Boolean {
        return true
    }

    override fun loadData() {

    }

    override fun beforeInitView() {

    }

    override fun afterInitViews() {

    }

    private fun initStatusBarMode() {
        if (isDarkMode) {
            StatusBarUtil.setStatusBarDarkMode(this)
        } else {
            StatusBarUtil.setStatusBarLightMode(this)
        }
    }

    override fun beforeOnCreate() {

    }

    override fun afterOnCreate() {}

    override fun initToolBarView(titleBarView: TitleBarView) {
        if (hasTitleBarBack()) {
            titleBarView.setLeftTextDrawable(R.mipmap.ic_back)
                .setOnLeftTextClickListener {
                    onBackPressed()
                }
        }
    }

    open fun isDarkMode(isDarkMode: Boolean) {
        this.isDarkMode = isDarkMode
    }

    override fun initEvents() {

    }

    override fun showLoading() {
        if (loadService == null) {
            loadService = LoadSir.getDefault().register(this) { onRetryBtnClick() }
        }
        loadService!!.showCallback(LoadingCallback::class.java)
    }

    override fun showLoading(view: View) {
        if (loadService == null) {
            loadService = LoadSir.getDefault().register(view) { onRetryBtnClick() }
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

    override fun attachBaseContext(newBase: Context) {
        if (shouldSupportMultiLanguage()) {
            val context = LanguageUtil.attachBaseContext(newBase)
            val configuration = context.resources.configuration
            // 此处的ContextThemeWrapper是androidx.appcompat.view包下的
            // 你也可以使用android.view.ContextThemeWrapper，但是使用该对象最低只兼容到API 17
            // 所以使用 androidx.appcompat.view.ContextThemeWrapper省心
            val wrappedContext: ContextThemeWrapper = object : ContextThemeWrapper(
                context,
                R.style.Theme_AppCompat_Empty
            ) {
                override fun applyOverrideConfiguration(overrideConfiguration: Configuration) {
                    overrideConfiguration.setTo(configuration)
                    super.applyOverrideConfiguration(overrideConfiguration)
                }
            }
            super.attachBaseContext(wrappedContext)
        } else {
            super.attachBaseContext(newBase)
        }
    }

    open fun shouldSupportMultiLanguage(): Boolean {
        return true
    }

    open fun immersionBarView(): View? {
        return null
    }

    open fun statusBarDarkMode(): Boolean {
        return true
    }

    open fun fullScreen(): Boolean {
        return false
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (touchHideSoft()) {
            if (ev.action == MotionEvent.ACTION_DOWN) {
                val v = currentFocus
                v?.let {
                    if (isShouldHideKeyboard(v, ev)) {
                        hideKeyboard(v.windowToken)
                    }
                }

            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 是否触摸edittext以外的隐藏软键盘
     */
    open fun touchHideSoft(): Boolean {
        return true
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     */
    private fun isShouldHideKeyboard(v: View, event: MotionEvent): Boolean {
        if (v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            // 点击EditText的事件，忽略它。
            return (event.x <= left || event.x >= right
                    || event.y <= top || event.y >= bottom)
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     */
    private fun hideKeyboard(token: IBinder?) {
        if (token != null) {
            val im = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    /**
     * 悬浮窗设置
     */
    @Suppress("DEPRECATION")
    private fun setSuspension() {
        val mParams = window.attributes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            //xxxx为你原来给低版本设置的Type
            mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        }
    }

    open fun canSwipeBack(): Boolean {
        return true
    }

    override fun initViews() {}
    private val mTheme: Int
        get() = R.style.AppTheme

    @SuppressLint("CheckResult")
    fun requestPermissions(vararg permissions: String) {
        XXPermissions.with(this)
            .permission(permissions)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: List<String>, all: Boolean) {
                    if (all) {
                        reqPermissionSuccess(permissions)
                    } else {
                        reqPermissionFailure(permissions)
                    }
                }

                override fun onDenied(permissions: List<String>, never: Boolean) {
                    if (never) {
                        reqPermissionNever(permissions)
                        XXPermissions.startPermissionActivity(this@BaseAc, permissions)
                    } else {
                        reqPermissionFailure(permissions)
                    }
                }
            })
    }

    open fun reqPermissionSuccess(permissions: List<String>) {

    }

    open fun reqPermissionFailure(permissions: List<String>) {

    }

    open fun reqPermissionNever(permissions: List<String>) {

    }

    private var doubleClickExitDetector: DoubleClickExitDetector? = null

    open fun isDoubleClickExit(): Boolean {
        return false
    }

    override fun onBackPressed() {
        if (isDoubleClickExit()) {
            val isExit = doubleClickExitDetector?.click()
            if (isExit != null && isExit) {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun onPause() {
        super.onPause()
        InputTools.hideInputMethod(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        mSavedInstanceState = null
    }
}