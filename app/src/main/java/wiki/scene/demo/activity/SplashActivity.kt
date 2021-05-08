package wiki.scene.demo.activity

import android.animation.Animator
import android.view.View
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.hjq.bar.TitleBar
import wiki.scene.demo.databinding.ActSplashBinding
import wiki.scene.lib_base.base_ac.BaseAc
import wiki.scene.lib_common.router.RouterPath

class SplashActivity : BaseAc<ActSplashBinding>() {

    override fun initToolBarView(titleBarView: TitleBar) {
        super.initToolBarView(titleBarView)
        titleBarView.visibility = View.GONE
    }

    override fun hasTitleBarBack(): Boolean {
        return false
    }

    override fun initViews() {
        super.initViews()
        binding.lottieView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                ARouter.getInstance()
                    .build(RouterPath.Main.ACT_MAIN_2)
                    .navigation(this@SplashActivity, object : NavCallback() {
                        override fun onArrival(postcard: Postcard?) {
                            finish()
                        }

                    })
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

        })
    }

    override fun onBackPressed() {

    }

    override fun canSwipeBack(): Boolean {
        return false
    }
}