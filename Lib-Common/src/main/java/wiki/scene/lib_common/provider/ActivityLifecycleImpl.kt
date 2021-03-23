package wiki.scene.lib_common.provider

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import java.lang.reflect.InvocationTargetException

/**
 * Created by zlx on 2020/9/23 13:57
 * Email: 1170762202@qq.com
 * Description:
 */
class ActivityLifecycleImpl {
    val applicationByReflect: Application?
        @SuppressLint("PrivateApi")
        get() {
            try {
                val activityThreadClass = Class.forName("android.app.ActivityThread")
                val thread = activityThread
                val app =
                    activityThreadClass.getMethod("getApplication").invoke(thread) ?: return null
                return app as Application
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }
            return null
        }
    private val activityThread: Any?
         get() {
            var activityThread = activityThreadInActivityThreadStaticField
            if (activityThread != null) return activityThread
            activityThread = activityThreadInActivityThreadStaticMethod
            return activityThread ?: activityThreadInLoadedApkField
        }
    private val activityThreadInActivityThreadStaticField: Any?
         @SuppressLint("PrivateApi")
         get() = try {
            val activityThreadClass = Class.forName("android.app.ActivityThread")
            val sCurrentActivityThreadField =
                activityThreadClass.getDeclaredField("sCurrentActivityThread")
            sCurrentActivityThreadField.isAccessible = true
            sCurrentActivityThreadField[null]
        } catch (e: Exception) {
            Log.e(
                "UtilsActivityLifecycle",
                "getActivityThreadInActivityThreadStaticField: " + e.message
            )
            null
        }
    private val activityThreadInActivityThreadStaticMethod: Any?
        @SuppressLint("PrivateApi")
        get() = try {
            val activityThreadClass = Class.forName("android.app.ActivityThread")
            activityThreadClass.getMethod("currentActivityThread").invoke(null)
        } catch (e: Exception) {
            Log.e(
                "UtilsActivityLifecycle",
                "getActivityThreadInActivityThreadStaticMethod: " + e.message
            )
            null
        }
    private val activityThreadInLoadedApkField: Any?
        get() = try {
            val mLoadedApkField = Application::class.java.getDeclaredField("mLoadedApk")
            mLoadedApkField.isAccessible = true
            val mLoadedApk = mLoadedApkField[applicationByReflect]
            val mActivityThreadField = mLoadedApk.javaClass.getDeclaredField("mActivityThread")
            mActivityThreadField.isAccessible = true
            mActivityThreadField[mLoadedApk]
        } catch (e: Exception) {
            Log.e("UtilsActivityLifecycle", "getActivityThreadInLoadedApkField: " + e.message)
            null
        }

    companion object {
        val INSTANCE = ActivityLifecycleImpl()
    }
}