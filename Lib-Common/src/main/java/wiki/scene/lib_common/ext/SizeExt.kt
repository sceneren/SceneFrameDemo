package wiki.scene.lib_common.ext

import com.blankj.utilcode.util.SizeUtils

fun Int.toPx(): Int {
    return SizeUtils.dp2px(this.toFloat())
}

fun Float.toPx(): Int {
    return SizeUtils.dp2px(this)
}