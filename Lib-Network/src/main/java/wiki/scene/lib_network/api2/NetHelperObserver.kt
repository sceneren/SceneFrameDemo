package wiki.scene.lib_network.api2

import androidx.lifecycle.LifecycleObserver
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import wiki.scene.lib_network.bean.ApiResponse
import wiki.scene.lib_network.util.RxExceptionUtil

class NetHelperObserver<T : ApiResponse<T>>(private val mCallback: NetCallback<T>?) : Observer<T>,
    LifecycleObserver {
    override fun onSubscribe(d: Disposable) {}
    override fun onNext(t: T) {
        if (mCallback != null) {
            if (t.isSuccess) {
                mCallback.onSuccess(t)
            } else {
                mCallback.onFail(t.errorMsg)
            }
        }
    }

    override fun onError(t: Throwable) {
        mCallback?.onFail(RxExceptionUtil.exceptionHandler(t))
    }

    override fun onComplete() {}
}