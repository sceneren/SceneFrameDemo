package wiki.scene.entity.base

class BaseResponse<T> {
    var errorCode: Int
    var errorMsg: String
    var data: T?

    constructor(code: Int, msg: String) {
        errorCode = code
        errorMsg = msg
        data = null
    }

    constructor(code: Int, msg: String, data: T) {
        errorCode = code
        errorMsg = msg
        this.data = data
    }

    val isSuccess: Boolean
        get() = errorCode == 0

    companion object {
        const val codeSuccess = 0
        const val codeError = 1
    }
}