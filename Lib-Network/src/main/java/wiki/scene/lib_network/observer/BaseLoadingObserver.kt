package wiki.scene.lib_network.observer

import com.blankj.utilcode.util.LogUtils
import com.kongzue.dialogx.dialogs.WaitDialog
import com.kongzue.dialogx.interfaces.DialogLifecycleCallback
import wiki.scene.lib_common.provider.dialog.CustomWaitDialog
import wiki.scene.lib_network.R
import wiki.scene.lib_network.exception.NetException

abstract class BaseLoadingObserver<T> : BaseObserver<T>() {

    override fun onStart() {
        super.onStart()
        CustomWaitDialog.show(R.string.lib_network_loading).dialogLifecycleCallback =
            object : DialogLifecycleCallback<WaitDialog>() {
                override fun onDismiss(dialog: WaitDialog?) {
                    super.onDismiss(dialog)
                    LogUtils.e("BaseLoadingObserver canceled")
                    this@BaseLoadingObserver.cancel()
                }
            }
    }

    override fun onSuccess(t: T) {

    }

    override fun onComplete() {
        super.onComplete()
        WaitDialog.dismiss()
    }

    override fun onFail(e: NetException.ResponseException) {
        WaitDialog.dismiss()
    }
}