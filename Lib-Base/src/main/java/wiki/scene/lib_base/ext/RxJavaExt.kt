package wiki.scene.lib_base.ext

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.changeNewThread(): Observable<T> {
    return this.observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.newThread())
}

fun <T> Observable<T>.changeIOThread(): Observable<T> {
    return this.observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}