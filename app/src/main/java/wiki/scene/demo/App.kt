package wiki.scene.demo

import org.koin.core.context.startKoin
import wiki.scene.demo.koin.appModule
import wiki.scene.lib_base.BaseApplication
import wiki.scene.lib_base.config.ModuleLifecycleConfig

class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }

        ModuleLifecycleConfig.getInstance().initModuleAhead(this)

    }
}