package wiki.scene.demo.fragment

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.aries.ui.view.title.TitleBarView
import com.blankj.utilcode.util.LogUtils
import wiki.scene.demo.databinding.FragTab1Binding
import wiki.scene.entity.ArticleListRes
import wiki.scene.entity.BannerInfo
import wiki.scene.entity.base.BaseResponse
import wiki.scene.lib_base.base_fg.BaseFg
import wiki.scene.lib_common.provider.router.RouterPath
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
                    .articleApi()
                    .banner()
            )
                .changeNew2MainThread()
                .bindLifecycle(getLifecycleTransformer())
                .subscribe(object : BaseLoadingObserver<MutableList<BannerInfo>>() {
                    override fun onSuccess(data: MutableList<BannerInfo>) {

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
