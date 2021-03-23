package wiki.scene.module_web.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.LinearLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import com.kongzue.dialogx.dialogs.BottomMenu
import wiki.scene.lib_base.base_ac.BaseAc
import wiki.scene.lib_base.constant.RouterActivityPath
import wiki.scene.module_web.R
import wiki.scene.module_web.WebLayout
import wiki.scene.module_web.databinding.ModuleWebAcWebBinding
import wiki.scene.module_web.databinding.ModuleWebAcWebTitleBarViewRightActionViewBinding


@Route(path = RouterActivityPath.Web.PAGER_WEB)
class WebAc : BaseAc<ModuleWebAcWebBinding>() {

    private var agentWeb: AgentWeb? = null

    @JvmField
    @Autowired(name = "webUrl")
    var url: String? = null

    override fun initToolBarView() {
        super.initToolBarView()
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
                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = Uri.parse(url)
                            startActivity(i)
                        }
                    }
                    return@setOnMenuItemClickListener false
                }
        }
        val rightViewAction = binding.titleBarView.ViewAction(rightActionView)
        binding.titleBarView.addRightAction(
            rightViewAction, 0,
            ViewGroup.LayoutParams(SizeUtils.dp2px(100F), SizeUtils.dp2px(30F))
        )

        binding.titleBarView.setOnLeftTextClickListener {
            backOrFinish()
        }
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
            binding.titleBarView.setTitleMainText(title)
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