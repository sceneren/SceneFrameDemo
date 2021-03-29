package wiki.scene.lib_base.base_fg

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.aries.ui.view.title.TitleBarView
import com.blankj.utilcode.util.LogUtils
import com.dylanc.viewbinding.base.inflateBindingWithGeneric
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import wiki.scene.lib_base.databinding.LibBaseTitleBarViewBinding
import wiki.scene.lib_base.loadsir.LoadingCallback

abstract class BaseFg<VB : ViewBinding> : Fragment() {
    var rootView: View? = null
    private var parent: ViewGroup? = null
    var mContext: Context? = null
    private var loadService: LoadService<*>? = null

    private var _binding: VB? = null
    open val binding: VB get() = _binding!!

    open var titleBarBinding: LibBaseTitleBarViewBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ARouter.getInstance().inject(this)
        _binding = inflateBindingWithGeneric(layoutInflater)
        rootView = binding.root

        parent = rootView!!.parent as ViewGroup?
        parent?.removeView(rootView)

        return rootView
    }

    open fun beforeCreateView() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (hasTitleBarView()) {
            titleBarBinding = LibBaseTitleBarViewBinding.bind(binding.root)
            initToolBarView(titleBarBinding!!.libBaseTvTitleBar)
        }
        initViews()
        loadData()
    }

    open fun loadData() {

    }

    open fun initViews() {}

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = context
    }

    override fun getView(): View? {
        return rootView
    }

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
}