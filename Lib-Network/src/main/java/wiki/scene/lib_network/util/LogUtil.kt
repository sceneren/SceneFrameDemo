package wiki.scene.lib_network.util

import android.util.Log
import wiki.scene.lib_network.BuildConfig

/**
 * Created by Zlx on 2017/5/3.
 */
object LogUtil {
    private const val TAG = "LogUtil"
    const val logSubLenth = 3000 //每行log长度
    fun show(content: String) {
        if (BuildConfig.DEBUG) {
            logSplit(TAG, content, 1)
        }
    }

    fun logSplit(explain: String, message: String, i: Int) {
        //TODO 添加非debug下不打印日志
        //        if (!BuildConfig.DEBUG) return;
        var i = i
        if (i > 10) return
        if (message.length <= logSubLenth) {
            Log.i(explain, "$explain$i：     $message")
            return
        }
        val msg1 = message.substring(0, logSubLenth)
        Log.i(explain, "$explain$i：     $msg1")
        val msg2 = message.substring(logSubLenth)
        logSplit(explain, msg2, ++i)
    }
}