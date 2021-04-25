package wiki.scene.lib_network.observer

import com.kongzue.dialogx.dialogs.WaitDialog
import wiki.scene.lib_network.R
import wiki.scene.lib_network.exception.NetException

abstract class BaseLoadingObserver<T> : BaseObserver<T>() {
    override fun onStart() {
        super.onStart()
        WaitDialog.show(R.string.lib_network_loading)
    }

    override fun onSuccess(t: T) {
        WaitDialog.dismiss()
    }

    override fun onFail(e: NetException.ResponseException) {
        WaitDialog.dismiss()
    }
}