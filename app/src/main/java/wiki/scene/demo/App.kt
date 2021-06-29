package wiki.scene.demo

import dagger.hilt.android.HiltAndroidApp
import wiki.scene.lib_base.BaseApplication
import wiki.scene.lib_base.config.ModuleLifecycleConfig

@HiltAndroidApp
class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        ModuleLifecycleConfig.getInstance().initModuleAhead(this)
    }
}