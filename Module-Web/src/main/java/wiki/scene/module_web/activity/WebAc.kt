package wiki.scene.module_web.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.webkit.WebView
import android.widget.LinearLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import com.kongzue.dialogx.dialogs.BottomMenu
import wiki.scene.lib_base.base_ac.BaseAc
import wiki.scene.lib_base.constant.RouterActivityPath
import wiki.scene.lib_network.util.LogUtil
import wiki.scene.module_web.R
import wiki.scene.module_web.WebLayout
import wiki.scene.module_web.databinding.ModuleWebAcWebBinding

@Route(path = RouterActivityPath.Web.PAGER_WEB)
class WebAc : BaseAc<ModuleWebAcWebBinding>() {
    private var url: String? = null
    override fun initViews() {
        super.initViews()
        setRightImg(R.mipmap.ic_more_menu)
        url = intent.getStringExtra("webUrl")
        LogUtil.show("webUrl=$url")
        val agentWeb = AgentWeb.with(this)
            .setAgentWebParent(binding.parent, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .setWebChromeClient(mWebChromeClient)
            .setWebViewClient(mWebViewClient)
            .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setWebLayout(WebLayout(this))
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK) //打开其他应用时，弹窗咨询用户是否前往其他应用
            .interceptUnkownUrl() //拦截找不到相关页面的Scheme
            .createAgentWeb()
            .ready()
            .go(url)
        binding.rlClose.setOnClickListener { finish() }
        binding.llRight.setOnClickListener {

            BottomMenu.show(arrayOf("刷新", "使用浏览器打开"))
                .setOnMenuItemClickListener { _, _, index ->
                    when (index) {
                        0 -> {
                            agentWeb.urlLoader.reload()
                        }
                        1 -> {
                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = Uri.parse(url)
                            startActivity(i)
                        }
                    }
                    return@setOnMenuItemClickListener false
                }
        }
    }

    private val mWebViewClient: WebViewClient = object : WebViewClient() {

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
        }
    }
    private val mWebChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            setAcTitle(title)
        }
    }
}