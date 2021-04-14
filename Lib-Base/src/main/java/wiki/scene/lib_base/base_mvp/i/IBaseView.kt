package wiki.scene.lib_base.base_mvp.i

import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


interface IBaseView {
    fun findActivity(): AppCompatActivity

    fun findFragment(): Fragment?

    fun findContext(): Context

    fun showLoading()

    fun showSuccess()

    fun showEmpty()

    fun showError()

    fun showToast(msg: String?)

    fun showToast(@StringRes stringResId: Int)

    fun onRetryBtnClick()
}