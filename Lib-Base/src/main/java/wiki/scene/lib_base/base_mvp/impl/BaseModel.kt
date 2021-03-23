package wiki.scene.lib_base.base_mvp.impl

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import wiki.scene.lib_base.base_mvp.i.IBaseModel

/**
 * @author chenke
 * @create 2020/6/30
 * @Describe
 */
open class BaseModel : IBaseModel, LifecycleObserver {

    /**
     * presenter在onDestroy时调用
     * 释放资源
     */
    override fun onDestroy() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this)
    }


}