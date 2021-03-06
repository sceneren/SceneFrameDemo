package wiki.scene.lib_base.module

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.AppUtils
import com.chad.library.adapter.base.module.LoadMoreModuleConfig
import com.hjq.bar.TitleBar
import com.hjq.permissions.XXPermissions
import com.hjq.toast.ToastUtils
import com.kingja.loadsir.core.LoadSir
import com.kongzue.dialogx.DialogX
import com.kongzue.dialogx.dialogs.TipDialog
import com.kongzue.dialogx.interfaces.BaseDialog.BOOLEAN
import com.kongzue.dialogx.style.IOSStyle
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.tencent.mmkv.MMKV
import me.jessyan.autosize.AutoSizeCompat
import me.jessyan.autosize.AutoSizeConfig
import wiki.scene.lib_base.BaseApplication
import wiki.scene.lib_base.BuildConfig
import wiki.scene.lib_base.adapters.loadmore.CustomLoadMoreView
import wiki.scene.lib_base.loadsir.EmptyCallback
import wiki.scene.lib_base.loadsir.ErrorCallback
import wiki.scene.lib_base.loadsir.LoadingCallback
import wiki.scene.lib_common.title.CustomBarStyle
import wiki.scene.lib_common.toast.CustomToastStyle
import wiki.scene.lib_db.manager.DbUtil

/**
 *
 * @Description:    初始化实际实现
 * @Author:         scene
 * @CreateDate:     2021/6/29 14:58
 * @UpdateUser:
 * @UpdateDate:     2021/6/29 14:58
 * @UpdateRemark:
 * @Version:        1.0.0
 */
class CommonModuleInit : IModuleInit {
    override fun onInitAhead(application: Application): Boolean {
        initAutoSize()
        initMMKV(application)
        initARouter(application)
        initToast(application)
        initPermission()
        initDialogX(application)

        initSmartRefreshLayout(application)
        initLoadMoreView()

        initLoadSir()
        initDB(application)
        initTitleBar()
        return false
    }

    override fun onInitAfter(application: BaseApplication): Boolean {

        return false
    }

    private fun initAutoSize() {
        AutoSizeConfig.getInstance().isExcludeFontScale = true
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
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { _: Context?, refreshLayout: RefreshLayout ->
            refreshLayout.setEnableLoadMore(false)
            ClassicsHeader(application)
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
        AutoSizeCompat.autoConvertDensityOfGlobal(application.resources)
        DialogX.init(application)
        DialogX.globalStyle = IOSStyle()
        DialogX.globalTheme = DialogX.THEME.LIGHT
        TipDialog.overrideCancelable = BOOLEAN.TRUE
    }

    private fun initTitleBar() {
        TitleBar.setDefaultStyle(CustomBarStyle())
    }
}