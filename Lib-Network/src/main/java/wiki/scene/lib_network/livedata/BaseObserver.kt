package wiki.scene.lib_network.livedata

import wiki.scene.lib_network.bean.ApiResponse
import wiki.scene.lib_network.popwindow.PopUtil

class BaseObserver<T>(private val baseObserverCallBack: BaseObserverCallBack<T>) :
    IBaseObserver<T> {
    override fun onChanged(t: T) {
        if (t is ApiResponse<*>) {
            val apiResponse = t as ApiResponse<*>
            if (apiResponse.isSuccess) {
                baseObserverCallBack.onSuccess(t)
            } else {
                baseObserverCallBack.onFail(apiResponse.errorMsg)
                if (baseObserverCallBack.showErrorMsg()) {
                    PopUtil.show(apiResponse.errorMsg)
                }
            }
        } else {
            baseObserverCallBack.onFail("系统繁忙!")
        }
        baseObserverCallBack.onFinish()
    }
}