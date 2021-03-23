package wiki.scene.lib_network.popwindow

import android.os.Handler
import android.os.Looper

/**
 * Created by zlx on 2020/9/28 9:34
 * Email: 1170762202@qq.com
 * Description: 全局通用弹窗工具类
 */
object PopUtil {
    @JvmOverloads
    fun show(msg: String, onPopCallBack: OnPopCallBack? = null) {
        val commonPop = CommonPop(msg)
        commonPop.showPopupWindow()
        Handler(Looper.getMainLooper()).postDelayed({
            if (commonPop != null && commonPop.isShowing) {
                commonPop.dismiss()
            }
            onPopCallBack?.dismiss()
        }, 2000)
    }

    interface OnPopCallBack {
        fun dismiss()
    }
}