package wiki.scene.lib_network.livedata

/**
 * Copyright (C)
 * FileName: BaseObserverCallBack
 * Author: Zlx
 * Email: 1170762202@qq.com
 * Date: 2020/9/17 10:30
 * Description:
 */
abstract class BaseObserverCallBack<T> {

    abstract fun onSuccess(data: T)

    open fun showErrorMsg(): Boolean {
        return false
    }

    open fun onFail(msg: String?) {}

    open fun onFinish() {}
}