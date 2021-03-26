package wiki.scene.lib_common.provider

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import com.hjq.toast.style.BaseToastStyle

class CustomToastStyle(context: Context?) : BaseToastStyle(context) {
    override fun getCornerRadius(): Int {
        return dp2px(8F)
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

    override fun getGravity(): Int {
        return Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
    }
}