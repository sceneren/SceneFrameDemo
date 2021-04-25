package wiki.scene.lib_common.provider.app

import android.app.Application

/**
 * FileName: AppProvider
 * Created by zlx on 2020/9/22 10:17
 * Email: 1170762202@qq.com
 * Description:
 */
class AppProvider(val app: Application) {

    companion object {
        private var INSTANCE: AppProvider? = null
        fun init(application: Application) {
            if (INSTANCE == null) {
                INSTANCE = AppProvider(application)
            }
        }

        val instance: AppProvider
            get() {
                if (INSTANCE == null) {
                    INSTANCE = AppProvider(AppBridge.applicationByReflect!!)
                }
                return INSTANCE!!
            }
    }
}