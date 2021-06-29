package wiki.scene.demo.fragment

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.TimeUtils
import com.hjq.bar.TitleBar
import com.hjq.toast.ToastUtils
import com.luck.picture.lib.entity.LocalMedia
import io.reactivex.Observable
import wiki.scene.demo.databinding.FragTab1Binding
import wiki.scene.lib_base.aop.checklogin.annotation.CheckLogin
import wiki.scene.lib_base.base_fg.BaseFg
import wiki.scene.lib_base.base_util.EnvironmentUtil
import wiki.scene.lib_base.ext.clicks
import wiki.scene.lib_base.picture.selector.OnChooseImageListener
import wiki.scene.lib_base.picture.selector.PictureSelectorUtil
import wiki.scene.lib_common.router.RouterPath
import wiki.scene.lib_db.entity.SearchHistoryEntity
import wiki.scene.lib_db.manager.DbUtil
import wiki.scene.lib_network.exception.NetException
import wiki.scene.lib_network.ext.bindLifecycle
import wiki.scene.lib_network.ext.changeIO2MainThread
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

    override fun initToolBarView(titleBarView: TitleBar) {
        super.initToolBarView(titleBarView)
        titleBarView.title = ("主页")
    }

    override fun hasTitleBarView(): Boolean {
        return true
    }

    override fun loadData() {

    }

    override fun initListener() {
        super.initListener()
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

    private fun showBetaMode() {
        showToast("当前是否是测试环境：${EnvironmentUtil.isBeta()}")
    }

    @CheckLogin
    private fun testNeedLogin() {
        showToast("测试需要登录")
    }
}
