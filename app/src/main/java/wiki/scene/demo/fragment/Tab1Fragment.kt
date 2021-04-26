package wiki.scene.demo.fragment

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.aries.ui.view.title.TitleBarView
import com.hjq.toast.ToastUtils
import wiki.scene.demo.databinding.FragTab1Binding
import wiki.scene.lib_base.base_fg.BaseFg
import wiki.scene.lib_common.provider.router.RouterPath
import wiki.scene.lib_network.exception.NetException
import wiki.scene.lib_network.ext.bindLifecycle
import wiki.scene.lib_network.ext.changeNew2MainThread
import wiki.scene.lib_network.manager.ApiManager
import wiki.scene.lib_network.observer.BaseLoadingObserver
import wiki.scene.lib_network.transform.ApiTransform

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
        binding.btnTab.setOnClickListener {
            ARouter.getInstance()
                .build(RouterPath.Main.ACT_MAIN)
                .navigation()
        }
        binding.btnTest.setOnClickListener {
            ARouter.getInstance()
                .build(RouterPath.Main.ACT_RECYCLERVIEW)
                .navigation()
        }

        binding.btnRecyclerViewStickyHeader.setOnClickListener {

            ApiTransform.transform(
                ApiManager.getInstance()
                    .loginApi()
                    .logout()
            )
                .changeNew2MainThread()
                .bindLifecycle(getLifecycleTransformer())
                .subscribe(object : BaseLoadingObserver<String>(true) {
                    override fun onSuccess(data: String) {
                        ToastUtils.show("成功：$data")
                    }

                    override fun onFail(e: NetException.ResponseException) {
                        super.onFail(e)
                        ToastUtils.show("失败 ：${e.message}")
                    }
                })

        }

        binding.btnMvpRecyclerView.setOnClickListener {
            ARouter.getInstance()
                .build(RouterPath.Main.ACT_MVP_RECYCLERVIEW)
                .navigation()
        }

    }

    override fun initToolBarView(titleBarView: TitleBarView) {
        super.initToolBarView(titleBarView)
        titleBarView.setTitleMainText("主页")
    }

    override fun hasTitleBarView(): Boolean {
        return true
    }

    override fun loadData() {
    }
}
