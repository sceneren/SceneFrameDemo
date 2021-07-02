package wiki.scene.module_web

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebView
import com.just.agentweb.IWebLayout

/**
 *
 * @Description:    web实现类
 * @Author:         scene
 * @CreateDate:     2021/7/2 09:54
 * @UpdateUser:
 * @UpdateDate:     2021/7/2 09:54
 * @UpdateRemark:
 * @Version:        1.0.0
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