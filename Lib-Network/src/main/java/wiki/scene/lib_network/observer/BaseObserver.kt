package wiki.scene.lib_network.observer

import io.reactivex.observers.DefaultObserver
import wiki.scene.lib_network.exception.NetException
import wiki.scene.lib_network.exception.NetException.ResponseException

abstract class BaseObserver<T> : DefaultObserver<T>() {

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        if (e is ResponseException) {
            error(e)
        } else {
            onError(ResponseException(e, NetException.ERROR.UNKNOWN))
        }
    }

    abstract fun onSuccess(t: T)
    abstract fun onFail(e: ResponseException)
    override fun onComplete() {}
}