package wiki.scene.lib_base.glide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import wiki.scene.lib_base.R

object GlideUtil {

    private fun getRoundTransformation(round: Int): MultiTransformation<Bitmap> {
        return MultiTransformation(
            CenterCrop(),
            RoundedCorners(round)
        )
    }

    private fun loadRoundTransform(
        context: Context,
        @DrawableRes drawableId: Int,
        round: Int
    ): RequestBuilder<Drawable> {

        return Glide.with(context)
            .load(drawableId)
            .apply(RequestOptions.bitmapTransform(getRoundTransformation(round)))
    }

    private fun loadRoundTransform(
        context: Context,
        url: String,
        round: Int
    ): RequestBuilder<Drawable> {
        return Glide.with(context)
            .load(url)
            .apply(RequestOptions.bitmapTransform(getRoundTransformation(round)))
    }

    private fun getCircleTransformation(): MultiTransformation<Bitmap> {
        return MultiTransformation(
            CenterCrop(),
            CircleCrop()
        )
    }

    private fun loadCircleTransformation(
        context: Context,
        @DrawableRes drawableId: Int
    ): RequestBuilder<Drawable> {

        return Glide.with(context)
            .load(drawableId)
            .apply(RequestOptions.bitmapTransform(getCircleTransformation()))
    }

    private fun loadCircleTransformation(
        context: Context,
        url: String
    ): RequestBuilder<Drawable> {
        return Glide.with(context)
            .load(url)
            .apply(RequestOptions.bitmapTransform(getCircleTransformation()))
    }

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
    fun loadRoundImage(
        iv: ImageView,
        url: String,
        round: Int = SizeUtils.dp2px(5F),
        placeholderRedId: Int = R.drawable.ic_default_image,
        errorResId: Int = R.drawable.ic_default_image
    ) {

        Glide.with(iv.context)
            .load(url)
            .transform(CenterCrop(), RoundedCorners(round))
            .thumbnail(loadRoundTransform(iv.context, placeholderRedId, round))
            .thumbnail(loadRoundTransform(iv.context, errorResId, round))
            .into(iv)
    }

    fun loadSizeImage(iv: ImageView, url: String, width: Int, height: Int) {
        Glide.with(iv.context)
            .load(url)
            .override(width, height)
            .centerCrop()
            .apply(RequestOptions().placeholder(R.drawable.picture_image_placeholder))
            .into(iv)
    }

    /**
     * 加载圆形图片
     */
    fun loadCircleImage(
        iv: ImageView,
        url: String,
        placeholderRedId: Int = R.drawable.ic_default_image,
        errorResId: Int = R.drawable.ic_default_image
    ) {
        Glide.with(iv.context)
            .load(url)
            .transform(CenterCrop(), CircleCrop())
            .thumbnail(loadCircleTransformation(iv.context, placeholderRedId))
            .thumbnail(loadCircleTransformation(iv.context, errorResId))
            .into(iv)
    }


    fun getBitmap(context: Context, imagePath: String): Bitmap? {
        return Glide.with(context)
            .asBitmap()
            .load(imagePath)
            .submit()
            .get()
    }

}