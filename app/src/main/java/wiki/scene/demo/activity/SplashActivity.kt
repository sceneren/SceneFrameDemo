package wiki.scene.demo.activity

import android.animation.Animator
import androidx.core.view.isGone
import com.alibaba.android.arouter.launcher.ARouter
import com.hjq.bar.TitleBar
import com.kongzue.dialogx.impl.AnimatorListenerEndCallBack
import wiki.scene.demo.databinding.ActSplashBinding
import wiki.scene.lib_base.BaseApplication
import wiki.scene.lib_base.base_ac.BaseAc
import wiki.scene.lib_base.config.ModuleLifecycleConfig
import wiki.scene.lib_common.router.RouterPath

class SplashActivity : BaseAc<ActSplashBinding>() {

    override fun initToolBarView(titleBarView: TitleBar) {
        super.initToolBarView(titleBarView)
        titleBarView.isGone = true
    }

    override fun fullScreen(): Boolean {
        return true
    }

    override fun hasTitleBarBack(): Boolean {
        return false
    }

    override fun afterSetContentView() {
        super.afterSetContentView()
        //在这初始化后面的东西
        ModuleLifecycleConfig.getInstance().initModuleAfter(BaseApplication.getInstance())
    }

    override fun initViews() {
        super.initViews()
        binding.lottieView.addAnimatorListener(object : AnimatorListenerEndCallBack() {
            override fun onAnimationEnd(animation: Animator?) {
                ARouter.getInstance()
                    .build(RouterPath.Main.ACT_MAIN)
                    .navigation()
            }

        })
    }

    override fun onBackPressed() {

    }

    override fun canSwipeBack(): Boolean {
        return false
    }
}