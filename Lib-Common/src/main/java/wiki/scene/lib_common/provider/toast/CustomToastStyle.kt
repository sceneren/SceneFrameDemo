package wiki.scene.lib_common.provider.toast

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import com.blankj.utilcode.util.SizeUtils
import com.hjq.toast.config.IToastStyle


class CustomToastStyle : IToastStyle<TextView> {
    override fun createView(context: Context): TextView {
        val textView = TextView(context)
        textView.id = android.R.id.message
        textView.gravity = getTextGravity()
        textView.setTextColor(getTextColor())
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getTextSize())
        val horizontalPadding: Int = getHorizontalPadding()
        val verticalPadding: Int = getVerticalPadding()

        // 适配布局反方向特性
        textView.setPaddingRelative(
            horizontalPadding,
            verticalPadding,
            horizontalPadding,
            verticalPadding
        )
        textView.layoutParams =
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        val background: Drawable = getBackgroundDrawable()
        // 设置背景
        textView.background = background
        // 设置 Z 轴阴影
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textView.z = getTranslationZ()
        }
        // 设置最大显示行数
        textView.maxLines = getMaxLines()
        return textView
    }

    override fun getYOffset(): Int {
        return SizeUtils.dp2px(100F)
    }

    override fun getGravity(): Int {
        return Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
    }

    private fun getTextColor(): Int {
        return Color.WHITE
    }

    private fun getTextGravity(): Int {
        return Gravity.CENTER
    }

    private fun getTextSize(): Float {
        return SizeUtils.dp2px(16F).toFloat()
    }

    private fun getHorizontalPadding(): Int {
        return SizeUtils.dp2px(24F)
    }

    private fun getVerticalPadding(): Int {
        return SizeUtils.dp2px(12F)
    }

    private fun getBackgroundDrawable(): Drawable {
        val drawable = GradientDrawable()
        // 设置颜色
        drawable.setColor(-0x78000000)
        // 设置圆角
        drawable.cornerRadius = SizeUtils.dp2px(8F).toFloat()
        return drawable
    }

    private fun getTranslationZ(): Float {
        return SizeUtils.dp2px(3F).toFloat()
    }

    private fun getMaxLines(): Int {
        return 3
    }

}