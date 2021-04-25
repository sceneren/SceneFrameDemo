package wiki.scene.lib_common.provider.router

import com.alibaba.android.arouter.launcher.ARouter
import wiki.scene.lib_common.provider.router.RouterPath

object RouterUtil {
    fun launchMain() {
        ARouter.getInstance().build(RouterPath.Main.ACT_MAIN).navigation()
    }

    fun launchWeb(webUrl: String?) {
        ARouter.getInstance().build(RouterPath.Web.ACT_WEB).withString("webUrl", webUrl)
            .navigation()
    }

    fun launchLogin() {
        ARouter.getInstance().build(RouterPath.Login.ACT_LOGIN).navigation()
    }
}