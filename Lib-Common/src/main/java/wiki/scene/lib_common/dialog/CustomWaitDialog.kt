package wiki.scene.lib_common.dialog

import android.view.View
import androidx.annotation.StringRes
import com.blankj.utilcode.util.StringUtils
import com.kongzue.dialogx.dialogs.WaitDialog
import com.kongzue.dialogx.interfaces.OnBindView
import wiki.scene.lib_common.R
import wiki.scene.lib_common.databinding.LibCommonWaitDialogCustomBinding

object CustomWaitDialog {

    fun show(@StringRes resId: Int): WaitDialog {
        return show(StringUtils.getString(resId))
    }

    fun show(msg: String): WaitDialog {
        return WaitDialog.show(msg)
            .setCustomView(object : OnBindView<WaitDialog>(R.layout.lib_common_wait_dialog_custom) {
                override fun onBind(dialog: WaitDialog, v: View) {
                    val binding = LibCommonWaitDialogCustomBinding.bind(v)
                    //binding.tvLoadingText.text = msg
                }

            })
    }
}