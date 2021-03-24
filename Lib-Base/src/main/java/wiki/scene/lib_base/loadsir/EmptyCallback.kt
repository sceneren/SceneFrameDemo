package wiki.scene.lib_base.loadsir

import android.content.Context
import android.view.View
import com.kingja.loadsir.callback.Callback
import wiki.scene.lib_base.R

/**
 * FileName: EmptyCallback
 * Created by zlx on 2020/9/17 17:37
 * Email: 1170762202@qq.com
 * Description:
 */
class EmptyCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.lib_base_layout_empty
    }

    //当前Callback的点击事件，如果返回true则覆盖注册时的onReload()，如果返回false则两者都执行，先执行onReloadEvent()。
    override fun onReloadEvent(context: Context, view: View): Boolean {
        return false
    }
}