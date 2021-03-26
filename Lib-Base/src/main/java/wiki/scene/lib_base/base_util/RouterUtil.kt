package wiki.scene.lib_base.base_util

import com.alibaba.android.arouter.launcher.ARouter
import wiki.scene.lib_base.constant.RouterPath

object RouterUtil {
    fun launchMain() {
        ARouter.getInstance().build(RouterPath.MAIN_ACT_MAIN).navigation()
    }

    fun launchWeb(webUrl: String?) {
        ARouter.getInstance().build(RouterPath.ACT_WEB).withString("webUrl", webUrl)
            .navigation()
    }

    fun launchLogin() {
        ARouter.getInstance().build(RouterPath.ACT_LOGIN).navigation()
    }
}