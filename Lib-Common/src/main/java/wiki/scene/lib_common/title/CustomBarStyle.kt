package wiki.scene.lib_common.title

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.hjq.bar.SelectorDrawable
import com.hjq.bar.style.CommonBarStyle
import wiki.scene.lib_common.R

class CustomBarStyle : CommonBarStyle() {
    override fun createLeftView(context: Context): TextView {
        val leftView = super.createLeftView(context)
        leftView.setTextColor(-0x99999a)
        setViewBackground(
            leftView, SelectorDrawable.Builder()
                .setDefault(ColorDrawable(0x00000000))
                .setFocused(ColorDrawable(0x0C000000))
                .setPressed(ColorDrawable(0x0C000000))
                .build()
        )
        return leftView
    }

    override fun createTitleView(context: Context): TextView {
        val titleView = super.createTitleView(context)
        titleView.setTextColor(-0xddddde)
        titleView.ellipsize = TextUtils.TruncateAt.END
        return titleView
    }

    override fun createRightView(context: Context): TextView {
        val rightView = super.createRightView(context)
        rightView.setTextColor(-0x5b5b5c)
        setViewBackground(
            rightView, SelectorDrawable.Builder()
                .setDefault(ColorDrawable(0x00000000))
                .setFocused(ColorDrawable(0x0C000000))
                .setPressed(ColorDrawable(0x0C000000))
                .build()
        )
        return rightView
    }

    override fun createLineView(context: Context): View {
        val lineView = super.createLineView(context)
        setViewBackground(lineView, ColorDrawable(-0x131314))
        return lineView
    }

    override fun createBackgroundDrawable(context: Context): Drawable {
        return ColorDrawable(-0x1)
    }

    override fun createBackIcon(context: Context): Drawable {
        return getDrawableResources(context, R.drawable.bar_arrows_left_black)
    }
}