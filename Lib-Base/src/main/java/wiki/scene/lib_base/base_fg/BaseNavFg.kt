package wiki.scene.lib_base.base_fg

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.inflateBindingWithGeneric

/**
 * Created by zlx on 2017/5/23.
 */
abstract class BaseNavFg<VB : ViewBinding> : Fragment() {
    private var mRootView: View? = null
    private var parent: ViewGroup? = null
    protected var mContext: Context? = null

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = inflateBindingWithGeneric(layoutInflater)
        mRootView = binding.root

        parent = mRootView!!.parent as ViewGroup
        parent?.removeView(mRootView)

        initViews()
        return mRootView
    }

    protected fun initViews() {}

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = getContext()
    }

    override fun getView(): View? {
        return mRootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}