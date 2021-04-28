package wiki.scene.lib_base.picture.selector

import android.graphics.Color
import com.luck.picture.lib.style.PictureSelectorUIStyle


class PictureStyle private constructor() {

    companion object {
        val instance: PictureStyle by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            PictureStyle()
        }
    }

    fun style(): PictureSelectorUIStyle {
        val mPictureSelectorUIStyle = PictureSelectorUIStyle()
        mPictureSelectorUIStyle.picture_album_textColor = Color.BLACK
        mPictureSelectorUIStyle.picture_statusBarBackgroundColor = Color.WHITE
        mPictureSelectorUIStyle.picture_statusBarChangeTextColor = true
        return mPictureSelectorUIStyle
    }

}