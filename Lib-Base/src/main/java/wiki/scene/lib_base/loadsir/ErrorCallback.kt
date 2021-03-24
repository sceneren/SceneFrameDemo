package wiki.scene.lib_base.loadsir

import com.kingja.loadsir.callback.Callback
import wiki.scene.lib_base.R

/**
 * FileName: ErrorCallback
 * Created by zlx on 2020/9/17 17:35
 * Email: 1170762202@qq.com
 * Description:
 */
class ErrorCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.lib_base_layout_error
    }
}