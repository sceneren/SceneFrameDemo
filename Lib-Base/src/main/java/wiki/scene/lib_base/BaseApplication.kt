package wiki.scene.lib_base

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.multidex.MultiDex

open class BaseApplication : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()
        setApplication(this)
    }

    /**
     * 当宿主没有继承自该Application时,可以使用set方法进行初始化baseApplication
     */
    private fun setApplication(application: BaseApplication) {
        instance = application
        application
            .registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {

                override fun onActivityCreated(
                    activity: Activity,
                    savedInstanceState: Bundle?
                ) {
                }

                override fun onActivityStarted(activity: Activity) {}
                override fun onActivityResumed(activity: Activity) {}
                override fun onActivityPaused(activity: Activity) {}
                override fun onActivityStopped(activity: Activity) {}
                override fun onActivitySaveInstanceState(
                    activity: Activity, outState: Bundle
                ) {
                }

                override fun onActivityDestroyed(activity: Activity) {}
            })
    }

    companion object {
        private var instance: BaseApplication? = null

        /**
         * 获得当前app运行的Application
         */
        fun getInstance(): BaseApplication {
            if (instance == null) {
                throw NullPointerException(
                    "please inherit BaseApplication or call setApplication."
                )
            }
            return instance!!
        }
    }
}