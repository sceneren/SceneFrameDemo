package wiki.scene.lib_base.indicator.entity

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.blankj.utilcode.util.ColorUtils
import wiki.scene.lib_base.R

/**
 *
 * @Description:    自定义的底部tab的实体类
 * @Author:         scene
 * @CreateDate:     2021/7/1 11:33
 * @UpdateUser:
 * @UpdateDate:     2021/7/1 11:33
 * @UpdateRemark:
 * @Version:        1.0.0
 */
data class CustomBottomTabInfo(
    val title: String = "",

    @DrawableRes val normalTabImageRes: Int = 0,

    @DrawableRes val selectTabImageRes: Int = 0,

    @ColorInt val normalTextColor: Int = ColorUtils.getColor(R.color.lib_base_colorTabTextUnSelect),

    @ColorInt val selectTextColor: Int = ColorUtils.getColor(R.color.lib_base_colorTabTextSelect)
)
