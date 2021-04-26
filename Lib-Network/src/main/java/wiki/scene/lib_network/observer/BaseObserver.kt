package wiki.scene.lib_network.observer

import io.reactivex.observers.DefaultObserver
import wiki.scene.lib_network.exception.NetException
import wiki.scene.lib_network.exception.NetException.ResponseException

abstract class BaseObserver<T>(private val canNull: Boolean = false) : DefaultObserver<T>() {

    override fun onNext(data: T) {
        onSuccess(data)
    }

    override fun onError(e: Throwable) {
        if (e is ResponseException) {
            if (e.code == NetException.ERROR.DATA_NULL && canNull) {
                onSuccess(e.message as T)
            } else {
                onFail(e)
            }
        } else {
            onError(NetException.handleException(e))
        }
    }

    abstract fun onSuccess(data: T)

    abstract fun onFail(e: ResponseException)

    override fun onComplete() {

    }
}