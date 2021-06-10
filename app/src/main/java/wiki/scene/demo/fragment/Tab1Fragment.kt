package wiki.scene.demo.fragment

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.hjq.bar.TitleBar
import com.hjq.toast.ToastUtils
import com.luck.picture.lib.entity.LocalMedia
import wiki.scene.demo.databinding.FragTab1Binding
import wiki.scene.lib_base.aop.checklogin.annotation.CheckLogin
import wiki.scene.lib_base.base_fg.BaseFg
import wiki.scene.lib_base.base_util.EnvironmentUtil
import wiki.scene.lib_base.ext.clicks
import wiki.scene.lib_base.picture.selector.OnChooseImageListener
import wiki.scene.lib_base.picture.selector.PictureSelectorUtil
import wiki.scene.lib_common.router.RouterPath
import wiki.scene.lib_network.exception.NetException
import wiki.scene.lib_network.ext.bindLifecycle
import wiki.scene.lib_network.ext.transformData
import wiki.scene.lib_network.manager.ApiManager
import wiki.scene.lib_network.observer.BaseLoadingObserver

@Route(path = RouterPath.Main.FRAG_TAB_1)
class Tab1Fragment : BaseFg<FragTab1Binding>() {
    @JvmField
    @Autowired
    var type = 0

    @JvmField
    @Autowired
    var name = ""

    override fun initViews() {
        super.initViews()
        binding.btnTab
            .clicks {
                ARouter.getInstance()
                    .build(RouterPath.Main.ACT_MAIN)
                    .navigation()
            }
        binding.btnTest
            .clicks {
                ARouter.getInstance()
                    .build(RouterPath.Main.ACT_RECYCLERVIEW)
                    .navigation()
            }

        binding.btnRecyclerViewStickyHeader
            .clicks {
                ApiManager.getInstance()
                    .loginApi()
                    .logout()
                    .transformData()
                    .bindLifecycle(getLifecycleTransformer())
                    .subscribe(object : BaseLoadingObserver<String>(true) {
                        override fun onSuccess(data: String) {
                            super.onSuccess(data)
                            ToastUtils.show("成功：$data")
                        }

                        override fun onFail(e: NetException.ResponseException) {
                            super.onFail(e)
                            ToastUtils.show("失败 ：${e.message}")
                        }
                    })
            }

        binding.btnMvpRecyclerView
            .clicks {
                ARouter.getInstance()
                    .build(RouterPath.Main.ACT_MVP_RECYCLERVIEW)
                    .navigation()
            }

        binding.btnChooseImage
            .clicks {
                PictureSelectorUtil.select(this, object : OnChooseImageListener() {
                    override fun onResult(result: MutableList<LocalMedia>?) {
                        LogUtils.e(GsonUtils.toJson(result))
                    }

                })
            }

        binding.btnIsBeta
            .clicks {
                showBetaMode()
            }


        binding.btnAopCheckLogin
            .clicks {
                testNeedLogin()
            }

    }

    private fun showBetaMode() {
        showToast("当前是否是测试环境：${EnvironmentUtil.isBeta()}")
    }

    @CheckLogin
    private fun testNeedLogin() {
        showToast("测试需要登录")
    }

    override fun initToolBarView(titleBarView: TitleBar) {
        super.initToolBarView(titleBarView)
        titleBarView.title = ("主页")
    }

    override fun hasTitleBarView(): Boolean {
        return true
    }

    override fun loadData() {
    }
}
