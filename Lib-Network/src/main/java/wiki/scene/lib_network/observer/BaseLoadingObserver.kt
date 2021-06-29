package wiki.scene.lib_network.observer

import androidx.annotation.StringRes
import com.kongzue.dialogx.dialogs.WaitDialog
import com.kongzue.dialogx.interfaces.DialogLifecycleCallback
import wiki.scene.lib_common.dialog.CustomWaitDialog
import wiki.scene.lib_network.R
import wiki.scene.lib_network.exception.NetException

abstract class BaseLoadingObserver<T>(
    @StringRes val msgResId: Int = R.string.lib_network_loading,
    canNull: Boolean = false
) :
    BaseObserver<T>(canNull) {

    override fun onStart() {
        super.onStart()
        CustomWaitDialog.show(msgResId).dialogLifecycleCallback =
            object : DialogLifecycleCallback<WaitDialog>() {
                override fun onDismiss(dialog: WaitDialog?) {
                    super.onDismiss(dialog)
                    this@BaseLoadingObserver.cancel()
                }
            }
    }

    override fun onSuccess(data: T) {
        WaitDialog.dismiss()
    }

    override fun onFail(e: NetException.ResponseException) {
        WaitDialog.dismiss()
    }
}