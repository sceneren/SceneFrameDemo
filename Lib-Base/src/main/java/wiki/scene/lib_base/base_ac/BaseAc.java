package wiki.scene.lib_base.base_ac;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.viewbinding.ViewBinding;

import com.dylanc.viewbinding.base.ViewBindingUtil;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.XXPermissions;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import java.util.List;

import wiki.scene.lib_base.R;
import wiki.scene.lib_base.base_manage.ActivityUtil;
import wiki.scene.lib_base.base_util.DoubleClickExitDetector;
import wiki.scene.lib_base.base_util.InputTools;
import wiki.scene.lib_base.base_util.LanguageUtil;
import wiki.scene.lib_base.base_util.LogUtils;
import wiki.scene.lib_base.impl.IAcView;
import wiki.scene.lib_base.impl.INetView;
import wiki.scene.lib_base.loadsir.EmptyCallback;
import wiki.scene.lib_base.loadsir.LoadingCallback;
import wiki.scene.lib_base.widget.slideback.SlideBack;

/**
 * Created by zlx on 2017/6/23.
 */

public abstract class BaseAc<VB extends ViewBinding> extends AppCompatActivity implements INetView, IAcView {

    private VB binding;
    protected TextView tvTitle;

    protected ImageView ivLeft;
    protected ImageView ivRight;

    private LoadService<?> loadService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        beforeOnCreate();
        super.onCreate(savedInstanceState);
        ActivityUtil.addActivity(this);
        afterOnCreate();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        setTheme(getMTheme());
        setSuspension();
        //绑定viewBinding
        binding = ViewBindingUtil.inflateWithGeneric(this, getLayoutInflater());
        setContentView(binding.getRoot());

        initImmersionBar();
        initEvents();
        initViews();
        doubleClickExitDetector =
                new DoubleClickExitDetector(this, "再按一次退出", 2000);

        if (canSwipeBack()) {
            //开启滑动返回
            SlideBack.create()
                    .attachToActivity(this);
        }
    }

    public VB getBinding() {
        return binding;
    }

    @Override
    public void beforeOnCreate() {

    }

    @Override
    public void afterOnCreate() {

    }

    @Override
    public void initEvents() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        ivLeft = (ImageView) findViewById(R.id.ivLeft);
        ivRight = (ImageView) findViewById(R.id.ivRight);
        if (ivLeft != null) {
            ivLeft.setOnClickListener(view -> finish());
        }
    }

    protected void setOnRightImgClickListener(View.OnClickListener listener) {
        if (ivRight != null) {
            ivRight.setOnClickListener(listener);
        }
    }

    @Override
    public void showLoading() {
        if (loadService == null) {
            loadService = LoadSir.getDefault().register(this, v -> onRetryBtnClick());
        }
        loadService.showCallback(LoadingCallback.class);
    }

    @Override
    public void showLoading(View view) {
        if (loadService == null) {
            loadService = LoadSir.getDefault().register(view, v -> onRetryBtnClick());
        }
        loadService.showCallback(LoadingCallback.class);
    }

    @Override
    public void showEmpty() {
        if (loadService == null) {
            loadService = LoadSir.getDefault().register(this, v -> onRetryBtnClick());
        }
        loadService.showCallback(EmptyCallback.class);
    }

    @Override
    public void showSuccess() {
        if (loadService == null) {
            loadService = LoadSir.getDefault().register(this, v -> onRetryBtnClick());
        }
        loadService.showSuccess();
    }

    @Override
    public void onRetryBtnClick() {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        if (shouldSupportMultiLanguage()) {
            Context context = LanguageUtil.attachBaseContext(newBase);
            final Configuration configuration = context.getResources().getConfiguration();
            // 此处的ContextThemeWrapper是androidx.appcompat.view包下的
            // 你也可以使用android.view.ContextThemeWrapper，但是使用该对象最低只兼容到API 17
            // 所以使用 androidx.appcompat.view.ContextThemeWrapper省心
            final ContextThemeWrapper wrappedContext = new ContextThemeWrapper(context,
                    R.style.Theme_AppCompat_Empty) {
                @Override
                public void applyOverrideConfiguration(Configuration overrideConfiguration) {
                    if (overrideConfiguration != null) {
                        overrideConfiguration.setTo(configuration);
                    }
                    super.applyOverrideConfiguration(overrideConfiguration);
                }
            };
            super.attachBaseContext(wrappedContext);
        } else {
            super.attachBaseContext(newBase);
        }
    }

    protected boolean shouldSupportMultiLanguage() {
        return true;
    }

    protected void setRightImg(int bg) {
        if (ivRight != null) {
            if (bg <= 0) {
                ivRight.setVisibility(View.GONE);
            } else {
                ivRight.setVisibility(View.VISIBLE);
                ivRight.setImageResource(bg);
            }
        }

    }

    protected void setLeftImg(int bg) {
        if (ivLeft != null) {
            if (bg <= 0) {
                ivLeft.setVisibility(View.GONE);
            } else {
                ivLeft.setVisibility(View.VISIBLE);
                ivLeft.setImageResource(bg);
            }
        }
    }


    @Override
    public void initImmersionBar() {
        if (!fullScreen()) {
            ImmersionBar.with(this)
                    .statusBarView(R.id.statusBarView)
                    .statusBarDarkFont(true)
                    .transparentBar()
                    .keyboardEnable(true)
                    .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                    .init();
        } else {
            ImmersionBar.with(this)
                    .fullScreen(true)
                    .keyboardEnable(true)
                    .hideBar(BarHide.FLAG_HIDE_BAR)
                    .init();
        }
    }


    protected boolean fullScreen() {
        return false;
    }

    protected void setAcTitle(String title) {
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (touchHideSoft()) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (isShouldHideKeyboard(v, ev)) {
                    hideKeyboard(v.getWindowToken());
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 是否触摸edittext以外的隐藏软键盘
     */
    protected boolean touchHideSoft() {
        return true;
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if ((v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            // 点击EditText的事件，忽略它。
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }


    /**
     * 获取InputMethodManager，隐藏软键盘
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 悬浮窗设置
     */
    private void setSuspension() {
        WindowManager.LayoutParams mParams = getWindow().getAttributes();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            //xxxx为你原来给低版本设置的Type
            mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
    }


    protected boolean canSwipeBack() {
        return true;
    }

    @Override
    public void initViews() {

    }

    protected int getMTheme() {
        return R.style.AppTheme;
    }


    @SuppressLint("CheckResult")
    public void requestPermissions(String... permissions) {
        XXPermissions.with(this)
                .permission(permissions)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            getPermissionSuccess();
                        } else {
                            getPermissionFailure();
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            XXPermissions.startPermissionActivity(BaseAc.this, permissions);
                        } else {
                            getPermissionFailure();
                        }
                    }
                });
    }

    public void getPermissionSuccess() {
        LogUtils.i("Base--->getPermissionSuccess");
    }

    public void getPermissionFailure() {
        LogUtils.i("Base--->getPermissionFail");
    }


    private DoubleClickExitDetector doubleClickExitDetector;

    public boolean isDoubleClickExit() {
        return false;
    }

    @Override
    public void onBackPressed() {
        if (isDoubleClickExit()) {
            boolean isExit = doubleClickExitDetector.click();
            if (isExit) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        InputTools.hideInputMethod(this);
    }


}
