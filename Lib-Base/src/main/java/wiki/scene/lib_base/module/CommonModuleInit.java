package wiki.scene.lib_base.module;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.AppUtils;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.module.LoadMoreModuleConfig;
import com.kingja.loadsir.core.LoadSir;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.tencent.mmkv.MMKV;

import wiki.scene.lib_base.BaseApplication;
import wiki.scene.lib_base.BuildConfig;
import wiki.scene.lib_base.adapters.loadmore.CustomLoadMoreView;
import wiki.scene.lib_base.loadsir.EmptyCallback;
import wiki.scene.lib_base.loadsir.ErrorCallback;
import wiki.scene.lib_base.loadsir.LoadingCallback;
import wiki.scene.lib_db.manager.DbUtil;

/**
 * Created by zlx on 2020/9/22 14:28
 * Email: 1170762202@qq.com
 * Description:
 */
public class CommonModuleInit implements IModuleInit {
    @Override
    public boolean onInitAhead(Application application) {
        LoadMoreModuleConfig.setDefLoadMoreView(new CustomLoadMoreView());
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> new ClassicsHeader(application));
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> new ClassicsFooter(application));
        MMKV.initialize(application);
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(application);
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new EmptyCallback())
                .setDefaultCallback(LoadingCallback.class)
                .commit();


        DbUtil.getInstance().init(application, AppUtils.getAppName());
        return false;
    }

    @Override
    public boolean onInitAfter(BaseApplication application) {
        return false;
    }
}
