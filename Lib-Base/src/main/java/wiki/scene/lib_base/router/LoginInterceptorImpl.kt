package wiki.scene.lib_base.router

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.blankj.utilcode.util.LogUtils
import wiki.scene.lib_base.mmkv.MMkvHelper
import wiki.scene.lib_common.router.RouterPath
import wiki.scene.lib_common.router.RouterUtil

@Interceptor(name = "Login", priority = 8)
class LoginInterceptorImpl : IInterceptor {
    override fun init(context: Context?) {
        LogUtils.v("The routing login interceptor is initialized successfully")
    }

    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        when (postcard.extra) {
            RouterPath.NEED_LOGIN -> {
                if (MMkvHelper.getInstance().isLogin()) {
                    RouterUtil.launchLogin()
                    callback.onInterrupt(null)
                } else {
                    callback.onContinue(postcard)
                }
            }
            else -> {
                callback.onContinue(postcard)
            }
        }
    }
}