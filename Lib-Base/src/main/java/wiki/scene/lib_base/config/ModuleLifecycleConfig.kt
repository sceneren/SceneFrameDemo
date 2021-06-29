package wiki.scene.lib_base.config

import android.app.Application
import wiki.scene.lib_base.BaseApplication
import wiki.scene.lib_base.module.CommonModuleInit
import java.io.Serializable

class ModuleLifecycleConfig private constructor() : Serializable {
    private val commonModuleInit by lazy { CommonModuleInit() }

    companion object {
        @JvmStatic
        fun getInstance(): ModuleLifecycleConfig {
            return SingletonHolder.mInstance
        }
    }

    private object SingletonHolder {
        //静态内部类
        val mInstance: ModuleLifecycleConfig = ModuleLifecycleConfig()
    }

    private fun readResolve(): Any {
        //防止单例对象在反序列化时重新生成对象
        return SingletonHolder.mInstance
    }

    /**
     * 优先初始化
     */
    fun initModuleAhead(application: Application) {

        commonModuleInit.onInitAhead(application)
    }

    /**
     * 后初始化
     */
    fun initModuleAfter(application: BaseApplication) {
        commonModuleInit.onInitAfter(application)
    }

}