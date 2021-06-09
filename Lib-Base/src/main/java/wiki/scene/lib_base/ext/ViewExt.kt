package wiki.scene.lib_base.ext

import android.view.View
import com.jakewharton.rxbinding4.view.clicks

fun View.clicks(block: (View) -> Unit) {
    this.clicks()
        .subscribe {
            block(this)
        }
}