package wiki.scene.lib_base.priview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ImageUtils
import com.bumptech.glide.Glide
import com.hjq.toast.ToastUtils
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import wiki.scene.lib_base.R
import wiki.scene.lib_base.base_ac.BaseAc
import wiki.scene.lib_network.exception.NetException
import wiki.scene.lib_network.ext.changeIO2MainThread
import wiki.scene.lib_network.observer.BaseLoadingObserver

class DownloadImageUtil private constructor() {

    companion object {
        val instance: DownloadImageUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            DownloadImageUtil()
        }
    }

    @SuppressLint("CheckResult")
    fun downloadImage(activity: AppCompatActivity, imagePath: String) {
        val observer = Observable.create { emitter: ObservableEmitter<Boolean> ->
            val bitmap = Glide.with(activity)
                .asBitmap()
                .load(imagePath)
                .submit()
                .get()
            if (bitmap == null) {
                emitter.onNext(false)
                emitter.onComplete()
            } else {
                val file = ImageUtils.save2Album(bitmap, Bitmap.CompressFormat.PNG, true)
                if (file == null) {
                    emitter.onNext(false)
                } else {
                    emitter.onNext(file.exists())
                }
                bitmap.recycle()
                emitter.onComplete()
            }

        }.changeIO2MainThread()
        if (activity is BaseAc<*>) {
            observer.compose(activity.bindToLifecycle())
        }

        observer.subscribe(object : BaseLoadingObserver<Boolean>() {
            override fun onSuccess(data: Boolean) {
                super.onSuccess(data)
                ToastUtils.show(R.string.lib_base_saved_successfully)
            }

            override fun onFail(e: NetException.ResponseException) {
                super.onFail(e)
                ToastUtils.show(R.string.lib_base_save_failed)
            }
        })
    }
}