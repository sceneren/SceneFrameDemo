package wiki.scene.lib_base.module

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.AppUtils
import com.chad.library.adapter.base.module.LoadMoreModuleConfig
import com.hjq.permissions.XXPermissions
import com.hjq.toast.ToastUtils
import com.kingja.loadsir.core.LoadSir
import com.kongzue.dialogx.DialogX
import com.kongzue.dialogx.dialogs.TipDialog
import com.kongzue.dialogx.interfaces.BaseDialog.BOOLEAN
import com.kongzue.dialogx.style.IOSStyle
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.tencent.mmkv.MMKV
import wiki.scene.lib_base.BaseApplication
import wiki.scene.lib_base.BuildConfig
import wiki.scene.lib_base.adapters.loadmore.CustomLoadMoreView
import wiki.scene.lib_base.loadsir.EmptyCallback
import wiki.scene.lib_base.loadsir.ErrorCallback
import wiki.scene.lib_base.loadsir.LoadingCallback
import wiki.scene.lib_common.toast.CustomToastStyle
import wiki.scene.lib_db.manager.DbUtil

/**
 * 在 ModuleLifecycleReflects 里面
 */
class CommonModuleInit : IModuleInit {
    override fun onInitAhead(application: Application): Boolean {
        initToast(application)
        initPermission()
        initDialogX(application)

        initSmartRefreshLayout(application)
        initLoadMoreView()

        initARouter(application)
        initMMKV(application)
        initLoadSir()
        initDB(application)
        return false
    }

    override fun onInitAfter(application: BaseApplication): Boolean {

        return false
    }

    private fun initMMKV(application: Application) {
        MMKV.initialize(application)
    }

    private fun initARouter(application: Application) {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(application)
    }

    private fun initLoadMoreView() {
        LoadMoreModuleConfig.defLoadMoreView = CustomLoadMoreView()
    }

    private fun initSmartRefreshLayout(application: Application) {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context: Context?, layout: RefreshLayout ->
            ClassicsHeader(application)
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context: Context?, layout: RefreshLayout ->
            ClassicsFooter(application)
        }
    }

    private fun initLoadSir() {
        LoadSir.beginBuilder()
            .addCallback(ErrorCallback())
            .addCallback(LoadingCallback())
            .addCallback(EmptyCallback())
            .setDefaultCallback(LoadingCallback::class.java)
            .commit()
    }

    private fun initDB(application: Application) {
        DbUtil.getInstance().init(application, AppUtils.getAppName())
    }

    private fun initToast(application: Application) {
        ToastUtils.init(application, CustomToastStyle())
    }

    private fun initPermission() {
        XXPermissions.setScopedStorage(true)
    }

    private fun initDialogX(application: Application) {
        DialogX.init(application)
        DialogX.globalStyle = IOSStyle()
        TipDialog.overrideCancelable = BOOLEAN.TRUE
    }
}