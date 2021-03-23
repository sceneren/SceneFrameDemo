package wiki.scene.lib_network.popwindow

import android.graphics.Color
import android.view.View
import android.view.animation.Animation
import android.widget.TextView
import razerdp.basepopup.BasePopupWindow
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.TranslationConfig
import wiki.scene.lib_common.provider.AppProvider
import wiki.scene.lib_network.R

/**
 * Created by zlx on 2020/9/28 9:10
 * Email: 1170762202@qq.com
 * Description: 全局通用弹窗
 */
class CommonPop(private val msg: String) : BasePopupWindow(AppProvider.instance.app) {
    private var textView: TextView? = null
    override fun onCreateContentView(): View {
        return createPopupById(R.layout.pop_common)
    }

    override fun onViewCreated(contentView: View) {
        super.onViewCreated(contentView)
        setBackgroundColor(Color.TRANSPARENT)
        textView = findViewById(R.id.text)
    }

    override fun showPopupWindow() {
        textView!!.text = msg
        super.showPopupWindow()
    }

    override fun onCreateShowAnimation(): Animation {
        return AnimationHelper.asAnimation()
            .withTranslation(TranslationConfig.FROM_TOP)
            .toShow()
    }

    override fun onCreateDismissAnimation(): Animation {
        return AnimationHelper.asAnimation()
            .withTranslation(TranslationConfig.TO_TOP)
            .toShow()
    }
}