package wiki.scene.lib_network.observer

import com.kongzue.dialogx.dialogs.WaitDialog
import com.kongzue.dialogx.interfaces.DialogLifecycleCallback
import wiki.scene.lib_common.provider.dialog.CustomWaitDialog
import wiki.scene.lib_network.R
import wiki.scene.lib_network.exception.NetException

abstract class BaseLoadingObserver<T>(canNull: Boolean = false) :
    BaseObserver<T>(canNull) {

    override fun onStart() {
        super.onStart()
        CustomWaitDialog.show(R.string.lib_network_loading).dialogLifecycleCallback =
            object : DialogLifecycleCallback<WaitDialog>() {
                override fun onDismiss(dialog: WaitDialog?) {
                    super.onDismiss(dialog)
                    this@BaseLoadingObserver.cancel()
                }
            }
    }

    override fun onComplete() {
        super.onComplete()
        WaitDialog.dismiss()
    }

    override fun onFail(e: NetException.ResponseException) {
        WaitDialog.dismiss()
    }
}