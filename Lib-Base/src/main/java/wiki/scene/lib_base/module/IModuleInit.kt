package wiki.scene.lib_base.module

import android.app.Application
import wiki.scene.lib_base.BaseApplication

/**
 *
 * @Description:    动态配置组件Application,有需要初始化的组件实现该接口,
 *                  统一在宿主app 的Application进行初始化
 *
 * @Author:         scene
 * @CreateDate:     2021/6/29 14:57
 * @UpdateUser:
 * @UpdateDate:     2021/6/29 14:57
 * @UpdateRemark:
 * @Version:        1.0.0
 */
interface IModuleInit {
    /**
     * 需要优先初始化的
     */
    fun onInitAhead(application: Application): Boolean

    /**
     * 可以后初始化的
     */
    fun onInitAfter(application: BaseApplication): Boolean
}