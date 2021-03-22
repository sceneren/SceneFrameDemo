package wiki.scene.lib_base.base_fg

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.inflateBindingWithGeneric
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import wiki.scene.lib_base.R
import wiki.scene.lib_base.base_util.LogUtils
import wiki.scene.lib_base.loadsir.LoadingCallback

/**
 * Created by zlx on 2017/5/23.
 */
abstract class BaseFg<VB : ViewBinding> : Fragment() {
    protected var rootView: View? = null
    private var parent: ViewGroup? = null
    protected var mContext: Context? = null
    private var loadService: LoadService<*>? = null

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBindingWithGeneric(layoutInflater)
        rootView = binding.root

        parent = rootView!!.parent as ViewGroup
        parent?.removeView(rootView)

        initViews()
        initImmersionBar()
        return rootView
    }

    protected fun initViews() {}
    private fun initImmersionBar() {
        if (immersionBar()) {
            ImmersionBar.with(this)
                .titleBar(R.id.statusBarView, false)
                .statusBarDarkFont(true)
                .keyboardEnable(true)
                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                .init()
        }
    }

    protected fun showLoading(view: View?) {
        if (loadService == null) {
            loadService = LoadSir.getDefault().register(view) { // 重新加载逻辑
                LogUtils.i("重新加载逻辑")
            }
        }
        loadService!!.showCallback(LoadingCallback::class.java)
    }

    protected fun showSuccess() {
        if (loadService != null) {
            loadService!!.showSuccess()
        }
    }

    protected fun immersionBar(): Boolean {
        return false
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = getContext()
    }

    override fun getView(): View? {
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}