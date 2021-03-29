package wiki.scene.lib_network.livedata

import com.blankj.utilcode.util.StringUtils
import com.kongzue.dialogx.dialogs.WaitDialog
import wiki.scene.lib_network.R

abstract class FastLoadingObserver<T> : FastObserver<T>() {
    override fun onStart() {
        super.onStart()
        WaitDialog.show(StringUtils.getString(R.string.lib_network_loading))
    }

    override fun onFinish() {
        super.onFinish()
        WaitDialog.dismiss()
    }
}