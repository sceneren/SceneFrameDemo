package wiki.scene.lib_base.base_fg

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.inflateBindingWithGeneric
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import wiki.scene.lib_base.base_util.LogUtils
import wiki.scene.lib_base.loadsir.LoadingCallback

/**
 * Created by zlx on 2017/5/23.
 */
abstract class BaseFg<VB : ViewBinding> : Fragment() {
    var rootView: View? = null
    private var parent: ViewGroup? = null
    var mContext: Context? = null
    private var loadService: LoadService<*>? = null

    private var _binding: VB? = null
    private val binding: VB get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBindingWithGeneric(layoutInflater)
        rootView = binding.root

        parent = rootView!!.parent as ViewGroup
        parent?.removeView(rootView)

        return rootView
    }

    open fun beforeCreateView() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
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
}