package wiki.scene.entity.base

data class BaseResponse<T>(
    var errorCode: Int = 0,
    var errorMsg: String = "",
    var data: T?
)
