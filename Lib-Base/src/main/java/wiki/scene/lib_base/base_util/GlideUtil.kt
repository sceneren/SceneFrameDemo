package wiki.scene.lib_base.base_util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

/**
 * FileName: GlideUtil
 * Created by zlx on 2020/9/18 16:02
 * Email: 1170762202@qq.com
 * Description:
 */
object GlideUtil {

    /**
     * 加载普通图片
     */
    fun loadImage(iv: ImageView, url: String) {
        Glide.with(iv.context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(iv)
    }

    fun loadImage(iv: ImageView, url: String, drawable: Drawable) {
        Glide.with(iv.context)
            .load(url)
            .apply(RequestOptions.errorOf(drawable).placeholder(drawable))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(iv)
    }

    fun loadImage(iv: ImageView, url: String, drawable: Int) {
        Glide.with(iv.context)
            .load(url)
            .apply(RequestOptions.errorOf(drawable).placeholder(drawable))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(iv)
    }

    /**
     * 加载圆角图片
     */
    fun loadRoundImage(iv: ImageView, url: String, round: Int = SizeUtils.dp2px(5F)) {
        Glide.with(iv.context)
            .load(url)
            .transform(CenterCrop(), RoundedCorners(round))
            .into(iv)
    }

    /**
     * 加载圆形图片
     */
    fun loadCircleImage(iv: ImageView, url: String) {
        Glide.with(iv.context)
            .load(url)
            .transition(DrawableTransitionOptions().crossFade())
            .transform(CircleCrop()).into(iv)
    }
}