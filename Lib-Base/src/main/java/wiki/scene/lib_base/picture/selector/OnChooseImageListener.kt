package wiki.scene.lib_base.picture.selector

import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener


abstract class OnChooseImageListener : OnResultCallbackListener<LocalMedia> {

    override fun onCancel() {
    }

}