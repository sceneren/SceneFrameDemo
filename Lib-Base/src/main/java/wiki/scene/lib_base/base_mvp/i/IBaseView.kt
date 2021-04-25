package wiki.scene.lib_base.base_mvp.i

import androidx.annotation.StringRes
import com.trello.rxlifecycle2.LifecycleTransformer


interface IBaseView {
    fun showLoading()

    fun showSuccess()

    fun showEmpty()

    fun showError()

    fun showToast(msg: String?)

    fun showToast(@StringRes stringResId: Int)

    fun onRetryBtnClick()

    fun <B> getLifecycleTransformer(): LifecycleTransformer<B>
}