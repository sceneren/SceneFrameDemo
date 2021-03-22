package wiki.scene.demo

import wiki.scene.lib_base.BaseApplication
import wiki.scene.lib_base.config.ModuleLifecycleConfig

class App :BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        ModuleLifecycleConfig.getInstance().initModuleAhead(this)
    }
}