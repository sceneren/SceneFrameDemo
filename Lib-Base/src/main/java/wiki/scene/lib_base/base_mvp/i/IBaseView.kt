package wiki.scene.lib_base.base_mvp.i

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


interface IBaseView {
    fun findActivity(): AppCompatActivity

    fun findFragment(): Fragment?

    fun findContext(): Context

    fun showLoadingPage()

    fun showErrorPage()

    fun showSuccessPage()

    fun showToast(msg: String?)
}