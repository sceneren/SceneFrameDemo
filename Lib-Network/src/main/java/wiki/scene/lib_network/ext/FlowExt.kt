package wiki.scene.lib_network.ext

import androidx.annotation.StringRes
import com.kongzue.dialogx.dialogs.WaitDialog
import com.kongzue.dialogx.interfaces.DialogLifecycleCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import wiki.scene.lib_common.dialog.CustomWaitDialog
import wiki.scene.lib_network.R

fun <T> Flow<T>.loadingCollect(
//    action: suspend FlowCollector<T>.() -> Unit
    @StringRes msgResId: Int = R.string.lib_network_loading
): Flow<T> {
    return this.flowOn(Dispatchers.IO)
        .onStart {
            CustomWaitDialog.show(msgResId).dialogLifecycleCallback =
                object : DialogLifecycleCallback<WaitDialog>() {
                    override fun onDismiss(dialog: WaitDialog?) {
                        super.onDismiss(dialog)
                    }
                }
        }
        .onCompletion {
            WaitDialog.dismiss()
        }
        .catch {
            WaitDialog.dismiss()
        }
}