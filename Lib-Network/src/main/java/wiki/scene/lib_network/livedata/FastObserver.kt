package wiki.scene.lib_network.livedata

import wiki.scene.lib_network.bean.ApiResponse
import wiki.scene.lib_network.popwindow.PopUtil

abstract class FastObserver<T> : BaseObserverCallBack<ApiResponse<T>>(),
    IBaseObserver<ApiResponse<T>> {

    override fun onChanged(t: ApiResponse<T>?) {
        if (t == null) {
            onFail("系统繁忙!")
        } else {
            if (t.isSuccess) {
                onSuccess(t)
            } else {
                onFail(t.errorMsg)
                if (showErrorMsg()) {
                    PopUtil.show(t.errorMsg)
                }
            }
        }
        onFinish()
    }


}