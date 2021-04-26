package wiki.scene.lib_common.app

import android.app.Application

/**
 * FileName: AppBridge
 * Created by zlx on 2020/9/22 10:22
 * Email: 1170762202@qq.com
 * Description:
 */
object AppBridge {
    val applicationByReflect: Application?
        get() = ActivityLifecycleImpl.getInstance().getApplicationByReflect()
}