package wiki.scene.lib_common.provider.toast

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import com.blankj.utilcode.util.SizeUtils
import com.hjq.toast.style.BaseToastStyle

class CustomToastStyle(context: Context?) : BaseToastStyle(context) {
    override fun getCornerRadius(): Int {
        return dp2px(4F)
    }

    override fun getBackgroundColor(): Int {
        return Color.parseColor("#88000000")
    }

    override fun getTextColor(): Int {
        return Color.parseColor("#EEFFFFFF")
    }

    override fun getTextSize(): Float {
        return sp2px(14F).toFloat()
    }

    override fun getPaddingStart(): Int {
        return dp2px(24F)
    }

    override fun getPaddingTop(): Int {
        return dp2px(8F)
    }

    override fun getYOffset(): Int {
        return SizeUtils.dp2px(60F)
    }

    override fun getGravity(): Int {
        return Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
    }
}