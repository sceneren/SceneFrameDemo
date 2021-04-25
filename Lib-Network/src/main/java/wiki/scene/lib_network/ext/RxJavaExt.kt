package wiki.scene.lib_network.ext

import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.changeNew2MainThread(): Observable<T> {
    return this.observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.newThread())
}

fun <T> Observable<T>.changeIO2MainThread(): Observable<T> {
    return this.observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}

fun <T> Observable<T>.bindLifecycle(
    lifecycleTransformer: LifecycleTransformer<T>,
): Observable<T> {
    return this.compose(lifecycleTransformer)

}