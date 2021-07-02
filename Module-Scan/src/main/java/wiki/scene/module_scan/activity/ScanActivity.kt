package wiki.scene.module_scan.activity

import android.graphics.Color
import cn.bingoogolapple.qrcode.core.BarcodeType
import cn.bingoogolapple.qrcode.core.QRCodeView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.hjq.bar.TitleBar
import com.hjq.permissions.Permission
import org.greenrobot.eventbus.EventBus
import wiki.scene.lib_base.base_ac.BaseAc
import wiki.scene.lib_base.ext.clicks
import wiki.scene.lib_common.router.RouterPath
import wiki.scene.module_scan.databinding.ModuleScanActScanBinding
import wiki.scene.module_scan.event.OnScanResultEvent

@Route(path = RouterPath.Scan.ACT_SCAN, extras = RouterPath.NEED_LOGIN)
class ScanActivity : BaseAc<ModuleScanActScanBinding>(), QRCodeView.Delegate {
    @JvmField
    @Autowired
    var tag: String? = null

    override fun hasTitleBarBack(): Boolean {
        return false
    }

    override fun initToolBarView(titleBarView: TitleBar) {
        super.initToolBarView(titleBarView)
        titleBarView.setBackgroundColor(Color.TRANSPARENT)
    }

    override fun initViews() {
        super.initViews()
        binding.zBar.setType(BarcodeType.ONLY_QR_CODE, null)
        binding.zBar.scanBoxView.isOnlyDecodeScanBoxArea = true
        binding.zBar.setDelegate(this)
        requestPermissions(Permission.CAMERA)
    }

    override fun initListener() {
        super.initListener()
        binding.layoutBack
            .clicks {
                onBackPressed()
            }
    }

    override fun onStart() {
        super.onStart()
        binding.zBar.startCamera()
        binding.zBar.startSpotAndShowRect()
    }

    override fun onStop() {
        binding.zBar.stopCamera()
        super.onStop()
    }

    override fun onDestroy() {
        binding.zBar.onDestroy()
        super.onDestroy()
    }

    override fun onScanQRCodeSuccess(result: String?) {
        result?.let {
            EventBus.getDefault().post(OnScanResultEvent(tag, it))
            finish()
        }
    }

    override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {
    }

    override fun onScanQRCodeOpenCameraError() {
    }

    override fun reqPermissionSuccess(permissions: List<String>) {
        super.reqPermissionSuccess(permissions)
        if (permissions.contains(Permission.CAMERA)) {
            binding.zBar.startCamera()
            binding.zBar.startSpotAndShowRect()
        }
    }
}