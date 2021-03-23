package wiki.scene.lib_common.provider

import android.app.Application
import android.util.Log

/**
 * FileName: AppProvider
 * Created by zlx on 2020/9/22 10:17
 * Email: 1170762202@qq.com
 * Description:
 */
class AppProvider(val app: Application) {

    companion object {
        const val TAG = "AppProvider"
        private var INSTANCE: AppProvider? = null
        fun init(application: Application) {
            if (INSTANCE == null) {
                Log.i(TAG, "init: AppProvider=contentprovider获取")
                INSTANCE = AppProvider(application)
            }
        }

        val instance: AppProvider
            get() {
                if (INSTANCE == null) {
                    Log.i(TAG, "init: AppProvider=反射获取")
                    INSTANCE = AppProvider(AppBridge.applicationByReflect!!)
                }
                return INSTANCE!!
            }
    }
}