package wiki.scene.lib_network.api2

import wiki.scene.lib_network.bean.ApiResponse

interface NetCallback<T : ApiResponse<T>> {
    fun onSuccess(response: T)
    fun onFail(msg: String)
}