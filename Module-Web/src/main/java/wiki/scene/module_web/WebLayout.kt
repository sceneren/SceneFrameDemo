package wiki.scene.module_web

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebView
import com.just.agentweb.IWebLayout

/**
 * Created by zlx on 2020/9/24 14:11
 * Email: 1170762202@qq.com
 * Description:
 */
class WebLayout(activity: Activity) : IWebLayout<WebView, ViewGroup> {

    private val mWebView by lazy {
        LayoutInflater.from(activity).inflate(R.layout.module_web_layout_web, null) as WebView
    }

    override fun getLayout(): ViewGroup {
        return mWebView
    }

    override fun getWebView(): WebView {
        return mWebView
    }
}