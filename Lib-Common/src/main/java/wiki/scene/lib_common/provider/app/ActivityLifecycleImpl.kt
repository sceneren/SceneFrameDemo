package wiki.scene.lib_common.provider.app

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import java.io.Serializable
import java.lang.reflect.InvocationTargetException

/**
 * Created by zlx on 2020/9/23 13:57
 * Email: 1170762202@qq.com
 * Description:
 */
class ActivityLifecycleImpl private constructor() : Serializable {
    companion object {
        @JvmStatic
        fun getInstance(): ActivityLifecycleImpl {//全局访问点
            return SingletonHolder.mInstance
        }
    }

    private object SingletonHolder {
        //静态内部类
        val mInstance: ActivityLifecycleImpl = ActivityLifecycleImpl()
    }

    private fun readResolve(): Any {//防止单例对象在反序列化时重新生成对象
        return SingletonHolder.mInstance
    }

    @SuppressLint("PrivateApi")
    fun getApplicationByReflect(): Application? {
        try {
            val activityThreadClass = Class.forName("android.app.ActivityThread")
            val thread: Any? = getActivityThread()
            val app = activityThreadClass.getMethod("getApplication").invoke(thread) ?: return null
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

    private fun getActivityThread(): Any? {
        var activityThread: Any? = getActivityThreadInActivityThreadStaticField()
        if (activityThread != null) return activityThread
        activityThread = getActivityThreadInActivityThreadStaticMethod()
        return activityThread ?: getActivityThreadInLoadedApkField()
    }

    @SuppressLint("PrivateApi")
    private fun getActivityThreadInActivityThreadStaticField(): Any? {
        return try {
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
    }

    @SuppressLint("PrivateApi")
    private fun getActivityThreadInActivityThreadStaticMethod(): Any? {
        return try {
            val activityThreadClass = Class.forName("android.app.ActivityThread")
            activityThreadClass.getMethod("currentActivityThread").invoke(null)
        } catch (e: Exception) {
            Log.e(
                "UtilsActivityLifecycle",
                "getActivityThreadInActivityThreadStaticMethod: " + e.message
            )
            null
        }
    }

    private fun getActivityThreadInLoadedApkField(): Any? {
        return try {
            val mLoadedApkField = Application::class.java.getDeclaredField("mLoadedApk")
            mLoadedApkField.isAccessible = true
            val mLoadedApk = mLoadedApkField[getApplicationByReflect()]
            val mActivityThreadField = mLoadedApk.javaClass.getDeclaredField("mActivityThread")
            mActivityThreadField.isAccessible = true
            mActivityThreadField[mLoadedApk]
        } catch (e: Exception) {
            null
        }
    }


}