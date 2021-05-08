package wiki.scene.module_web.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.webkit.WebView
import android.widget.LinearLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.hjq.bar.TitleBar
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import com.kongzue.dialogx.dialogs.BottomMenu
import wiki.scene.lib_base.base_ac.BaseAc
import wiki.scene.lib_common.router.RouterPath
import wiki.scene.lib_common.title.addCustomRightView
import wiki.scene.module_web.R
import wiki.scene.module_web.WebLayout
import wiki.scene.module_web.databinding.ModuleWebAcWebBinding
import wiki.scene.module_web.databinding.ModuleWebAcWebTitleBarViewRightActionViewBinding


@Route(path = RouterPath.Web.ACT_WEB)
class WebAc : BaseAc<ModuleWebAcWebBinding>() {

    private var agentWeb: AgentWeb? = null

    @JvmField
    @Autowired(name = "webUrl")
    var url: String? = null

    override fun initToolBarView(titleBarView: TitleBar) {
        super.initToolBarView(titleBarView)
        val rightActionView = View.inflate(
            mContext,
            R.layout.module_web_ac_web_title_bar_view_right_action_view,
            null
        )

        val actionBinding = ModuleWebAcWebTitleBarViewRightActionViewBinding.bind(rightActionView)

        actionBinding.rlClose.setOnClickListener {
            finish()
        }

        actionBinding.rlMore.setOnClickListener {
            BottomMenu.show(arrayOf("刷新", "使用浏览器打开"))
                .setCancelButton("取消")
                .setOnMenuItemClickListener { _, _, index ->
                    when (index) {
                        0 -> {
                            this.agentWeb!!.urlLoader.reload()
                        }
                        1 -> {
                            try {
                                val i = Intent(Intent.ACTION_VIEW)
                                i.data = Uri.parse(url)
                                startActivity(i)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }
                    return@setOnMenuItemClickListener false
                }
        }
        titleBarView.addCustomRightView(rightActionView, 100F, 30F)
    }

    override fun onTitleLeftClick() {
        backOrFinish()
    }

    override fun initViews() {
        super.initViews()

        LogUtils.d("webUrl=$url")

        agentWeb = AgentWeb.with(this)
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
    }

    private val mWebViewClient: WebViewClient = object : WebViewClient() {

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        }
    }
    private val mWebChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            titleBarBinding?.libBaseTvTitleBar?.title = title
        }
    }

    override fun onBackPressed() {
        backOrFinish()
    }

    private fun backOrFinish() {
        if (agentWeb == null) {
            finish()
        } else {
            if (agentWeb!!.webCreator.webView.canGoBack()) {
                agentWeb!!.back()
            } else {
                finish()
            }
        }
    }
}