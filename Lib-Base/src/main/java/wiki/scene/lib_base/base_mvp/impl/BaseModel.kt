package wiki.scene.lib_base.base_mvp.impl

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.blankj.utilcode.util.LogUtils
import wiki.scene.lib_base.base_mvp.i.IBaseModel

open class BaseModel : IBaseModel, LifecycleObserver {

    /**
     * presenter在onDestroy时调用
     * 释放资源
     */
    override fun onDestroy() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        LogUtils.i("释放Model")
        owner.lifecycle.removeObserver(this)
    }


}