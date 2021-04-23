package wiki.scene.lib_base.config

import android.app.Application
import wiki.scene.lib_base.BaseApplication
import wiki.scene.lib_base.module.IModuleInit
import java.io.Serializable

class ModuleLifecycleConfig private constructor() : Serializable {
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

    private fun readResolve(): Any {//防止单例对象在反序列化时重新生成对象
        return SingletonHolder.mInstance
    }

    /**
     * 优先初始化
     */
    fun initModuleAhead(application: Application?) {
        for (moduleName in ModuleLifecycleReflects.initModuleNames) {
            try {
                val clazz = Class.forName(moduleName)
                val init = clazz.newInstance() as IModuleInit
                init.onInitAhead(application!!)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 后初始化
     */
    fun initModuleAfter(application: BaseApplication?) {
        for (moduleName in ModuleLifecycleReflects.initModuleNames) {
            try {
                val clazz = Class.forName(moduleName)
                val init = clazz.newInstance() as IModuleInit
                // 调用初始化方法
                init.onInitAfter(application!!)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
    }

}