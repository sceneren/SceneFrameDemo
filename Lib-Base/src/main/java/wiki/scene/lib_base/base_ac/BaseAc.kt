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
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.inflateBindingWithGeneric
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import wiki.scene.lib_base.R
import wiki.scene.lib_base.base_manage.ActivityUtil
import wiki.scene.lib_base.base_util.DoubleClickExitDetector
import wiki.scene.lib_base.base_util.InputTools
import wiki.scene.lib_base.base_util.LanguageUtil
import wiki.scene.lib_base.base_util.LogUtils
import wiki.scene.lib_base.impl.IAcView
import wiki.scene.lib_base.impl.INetView
import wiki.scene.lib_base.loadsir.EmptyCallback
import wiki.scene.lib_base.loadsir.LoadingCallback
import wiki.scene.lib_base.widget.slideback.SlideBack

/**
 * Created by zlx on 2017/6/23.
 */
abstract class BaseAc<VB : ViewBinding> : AppCompatActivity(), INetView, IAcView {
    lateinit var binding: VB
    protected var tvTitle: TextView? = null
    protected var ivLeft: ImageView? = null
    protected var ivRight: ImageView? = null
    private var loadService: LoadService<*>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        beforeOnCreate()
        super.onCreate(savedInstanceState)
        ActivityUtil.addActivity(this)
        afterOnCreate()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT //竖屏
        setTheme(mTheme)
        setSuspension()
        //绑定viewBinding
        binding = this.inflateBindingWithGeneric(layoutInflater)
        setContentView(binding.root)
        initImmersionBar()
        initEvents()
        initViews()
        doubleClickExitDetector = DoubleClickExitDetector(this, "再按一次退出", 2000)
        if (canSwipeBack()) {
            //开启滑动返回
            SlideBack.create()
                .attachToActivity(this)
        }
    }

    override fun beforeOnCreate() {}
    override fun afterOnCreate() {}
    override fun initEvents() {
        tvTitle = findViewById<View>(R.id.tvTitle) as TextView
        ivLeft = findViewById<View>(R.id.ivLeft) as ImageView
        ivRight = findViewById<View>(R.id.ivRight) as ImageView
        if (ivLeft != null) {
            ivLeft!!.setOnClickListener { view: View? -> finish() }
        }
    }

    protected fun setOnRightImgClickListener(listener: View.OnClickListener?) {
        if (ivRight != null) {
            ivRight!!.setOnClickListener(listener)
        }
    }

    override fun showLoading() {
        if (loadService == null) {
            loadService = LoadSir.getDefault().register(this) { v: View? -> onRetryBtnClick() }
        }
        loadService!!.showCallback(LoadingCallback::class.java)
    }

    override fun showLoading(view: View) {
        if (loadService == null) {
            loadService = LoadSir.getDefault().register(view) { v: View? -> onRetryBtnClick() }
        }
        loadService!!.showCallback(LoadingCallback::class.java)
    }

    override fun showEmpty() {
        if (loadService == null) {
            loadService = LoadSir.getDefault().register(this) { v: View? -> onRetryBtnClick() }
        }
        loadService!!.showCallback(EmptyCallback::class.java)
    }

    override fun showSuccess() {
        if (loadService == null) {
            loadService = LoadSir.getDefault().register(this) { v: View? -> onRetryBtnClick() }
        }
        loadService!!.showSuccess()
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
                    if (overrideConfiguration != null) {
                        overrideConfiguration.setTo(configuration)
                    }
                    super.applyOverrideConfiguration(overrideConfiguration)
                }
            }
            super.attachBaseContext(wrappedContext)
        } else {
            super.attachBaseContext(newBase)
        }
    }

    protected fun shouldSupportMultiLanguage(): Boolean {
        return true
    }

    protected fun setRightImg(bg: Int) {
        if (ivRight != null) {
            if (bg <= 0) {
                ivRight!!.visibility = View.GONE
            } else {
                ivRight!!.visibility = View.VISIBLE
                ivRight!!.setImageResource(bg)
            }
        }
    }

    protected fun setLeftImg(bg: Int) {
        if (ivLeft != null) {
            if (bg <= 0) {
                ivLeft!!.visibility = View.GONE
            } else {
                ivLeft!!.visibility = View.VISIBLE
                ivLeft!!.setImageResource(bg)
            }
        }
    }

    override fun initImmersionBar() {
        if (!fullScreen()) {
            ImmersionBar.with(this)
                .statusBarView(R.id.statusBarView)
                .statusBarDarkFont(true)
                .transparentBar()
                .keyboardEnable(true)
                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                .init()
        } else {
            ImmersionBar.with(this)
                .fullScreen(true)
                .keyboardEnable(true)
                .hideBar(BarHide.FLAG_HIDE_BAR)
                .init()
        }
    }

    protected fun fullScreen(): Boolean {
        return false
    }

    protected fun setAcTitle(title: String?) {
        if (tvTitle != null) {
            tvTitle!!.text = title
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (touchHideSoft()) {
            if (ev.action == MotionEvent.ACTION_DOWN) {
                val v = currentFocus
                if (isShouldHideKeyboard(v, ev)) {
                    hideKeyboard(v.windowToken)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 是否触摸edittext以外的隐藏软键盘
     */
    protected fun touchHideSoft(): Boolean {
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
    private fun setSuspension() {
        val mParams = window.attributes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            //xxxx为你原来给低版本设置的Type
            mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        }
    }

    protected fun canSwipeBack(): Boolean {
        return true
    }

    override fun initViews() {}
    protected val mTheme: Int
        protected get() = R.style.AppTheme

    @SuppressLint("CheckResult")
    fun requestPermissions(vararg permissions: String?) {
        XXPermissions.with(this)
            .permission(permissions)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: List<String>, all: Boolean) {
                    if (all) {
                        permissionSuccess
                    } else {
                        permissionFailure
                    }
                }

                override fun onDenied(permissions: List<String>, never: Boolean) {
                    if (never) {
                        XXPermissions.startPermissionActivity(this@BaseAc, permissions)
                    } else {
                        permissionFailure
                    }
                }
            })
    }

    val permissionSuccess: Unit
        get() {
            LogUtils.i("Base--->getPermissionSuccess")
        }
    val permissionFailure: Unit
        get() {
            LogUtils.i("Base--->getPermissionFail")
        }
    private var doubleClickExitDetector: DoubleClickExitDetector? = null
    val isDoubleClickExit: Boolean
        get() = false

    override fun onBackPressed() {
        if (isDoubleClickExit) {
            val isExit = doubleClickExitDetector!!.click()
            if (isExit) {
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
}