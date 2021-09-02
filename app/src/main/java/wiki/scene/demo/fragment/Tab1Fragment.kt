package wiki.scene.demo.fragment

import android.os.SystemClock
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.TimeUtils
import com.hjq.bar.TitleBar
import com.hjq.toast.ToastUtils
import com.luck.picture.lib.entity.LocalMedia
import com.zhpan.bannerview.BannerViewPager
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.Subscribe
import wiki.scene.demo.adapter.Tab1BannerAdapter
import wiki.scene.demo.databinding.FragTab1Binding
import wiki.scene.demo.mvp.contract.Tab1FragmentContract
import wiki.scene.demo.mvp.presenter.Tab1FragmentPresenter
import wiki.scene.entity.BannerInfo
import wiki.scene.lib_base.aop.checklogin.annotation.CheckLogin
import wiki.scene.lib_base.base_mvp.BaseMvpFg
import wiki.scene.lib_base.base_util.EnvironmentUtil
import wiki.scene.lib_base.ext.clicks
import wiki.scene.lib_base.picture.selector.OnChooseImageListener
import wiki.scene.lib_base.picture.selector.PictureSelectorUtil
import wiki.scene.lib_common.ext.toPx
import wiki.scene.lib_common.router.RouterPath
import wiki.scene.lib_db.entity.SearchHistoryEntity
import wiki.scene.lib_db.manager.DbUtil
import wiki.scene.lib_network.exception.NetException
import wiki.scene.lib_network.ext.bindLifecycle
import wiki.scene.lib_network.ext.changeIO2MainThread
import wiki.scene.lib_network.ext.loadingCollect
import wiki.scene.lib_network.ext.transformData
import wiki.scene.lib_network.manager.ApiManager
import wiki.scene.lib_network.observer.BaseLoadingObserver
import wiki.scene.module_scan.event.OnScanResultEvent

@Route(path = RouterPath.Main.FRAG_TAB_1)
class Tab1Fragment : BaseMvpFg<FragTab1Binding, Tab1FragmentPresenter>(),
    Tab1FragmentContract.IView {

    override fun allowEventBus(): Boolean {
        return true
    }

    override fun initToolBarView(titleBarView: TitleBar) {
        super.initToolBarView(titleBarView)
        titleBarView.title = ("主页")
    }

    override fun hasTitleBarView(): Boolean {
        return true
    }

    override fun initViews() {
        super.initViews()
        initBanner()

        GlobalScope.launch(Dispatchers.Main) {
            flow {
                for (i in 1..10) {
                    SystemClock.sleep(1000)
                    emit("当前是第${i}次")
                }
            }.flowOn(Dispatchers.IO)
                .loadingCollect()
                .collect {
                    LogUtils.e(it)
                }

        }
    }

    override fun loadData() {
        mPresenter?.banner()
    }


    override fun initListener() {
        super.initListener()

        binding.btnTest
            .clicks {
                ARouter.getInstance()
                    .build(RouterPath.Main.ACT_RECYCLERVIEW)
                    .navigation()
            }

        binding.btnTestDataNull
            .clicks {
                ApiManager.getInstance()
                    .loginApi()
                    .logout()
                    .transformData()
                    .bindLifecycle(getLifecycleTransformer())
                    .subscribe(object : BaseLoadingObserver<String>(canNull = true) {
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

        binding.btnScanQrcode
            .clicks {
                ARouter.getInstance()
                    .build(RouterPath.Scan.ACT_SCAN)
                    .withString("tag", this.javaClass.simpleName)
                    .navigation()
            }

        binding.btnQueryHistory
            .clicks {
                Observable.create<List<SearchHistoryEntity>> {
                    it.onNext(
                        DbUtil.getInstance()
                            .getAppDataBase()
                            .searchHistoryDao()
                            .selectHis()
                    )
                }.changeIO2MainThread()
                    .bindLifecycle(getLifecycleTransformer())
                    .subscribe(object : BaseLoadingObserver<List<SearchHistoryEntity>>() {
                        override fun onStart() {
                            super.onStart()
                            LogUtils.e(TimeUtils.getNowString())
                        }

                        override fun onSuccess(data: List<SearchHistoryEntity>) {
                            super.onSuccess(data)
                            LogUtils.e(TimeUtils.getNowString())
                            showToast("查询成功")
                            LogUtils.e(data.toString())
                        }
                    })

            }

        binding.btnInsertHistory
            .clicks {
                //建议在model层处理，这儿只是测试下
                Observable.create<Long> {
                    val rowId = DbUtil.getInstance()
                        .getAppDataBase()
                        .searchHistoryDao()
                        .insertPerson(SearchHistoryEntity(name = binding.etText.text.toString()))
                    it.onNext(rowId)
                }.changeIO2MainThread()
                    .bindLifecycle(getLifecycleTransformer())
                    .subscribe(object : BaseLoadingObserver<Long>() {
                        override fun onStart() {
                            super.onStart()
                            LogUtils.e(TimeUtils.getNowString())
                        }

                        override fun onSuccess(data: Long) {
                            super.onSuccess(data)
                            LogUtils.e(TimeUtils.getNowString())
                            if (data > 0) {
                                showToast("添加成功")
                            }
                        }
                    })

            }
    }

    private fun initBanner() {
        (binding.bannerViewPager as BannerViewPager<BannerInfo>)
            .run {
                adapter = Tab1BannerAdapter()
                setLifecycleRegistry(lifecycle)
                setIndicatorHeight(6.toPx())
                setIndicatorSliderWidth(6.toPx(), 16.toPx())
            }.create()
    }

    private fun showBetaMode() {
        showToast("当前是否是测试环境：${EnvironmentUtil.isBeta()}")
    }

    @CheckLogin
    private fun testNeedLogin() {
        showToast("测试需要登录")
    }

    @Subscribe
    fun onScanResult(event: OnScanResultEvent?) {
        event?.let {
            if (event.tag == this.javaClass.simpleName) {
                showToast(event.result)
            }
        }
    }

    override fun initPresenter() {
        mPresenter = Tab1FragmentPresenter(this)
    }

    override fun bindBanner(data: MutableList<BannerInfo>) {
        binding.bannerViewPager.refreshData(data)
    }
}
