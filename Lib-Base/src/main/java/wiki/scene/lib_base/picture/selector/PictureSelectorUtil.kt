package wiki.scene.lib_base.picture.selector

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType


object PictureSelectorUtil {
    fun select(
        activity: AppCompatActivity,
        listener: OnChooseImageListener,
        maxSelectCount: Int = 1,
        crop: Boolean = false,
        compress: Boolean = true
    ) {
        PictureSelector.create(activity)
            .openGallery(PictureMimeType.ofAll())
            .imageEngine(GlideEngine.instance)
            .isEnableCrop(crop)
            .isCompress(compress)
            .maxSelectNum(maxSelectCount)
            .forResult(listener)
    }

    fun select(
        fragment: Fragment,
        listener: OnChooseImageListener,
        maxSelectCount: Int = 1,
        crop: Boolean = false,
        compress: Boolean = true
    ) {
        PictureSelector.create(fragment)
            .openGallery(PictureMimeType.ofAll())
            .imageEngine(GlideEngine.instance)
            .isEnableCrop(crop)
            .isCompress(compress)
            .maxSelectNum(maxSelectCount)
            .forResult(listener)
    }
}