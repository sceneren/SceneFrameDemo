package wiki.scene.lib_network.transform

import com.blankj.utilcode.util.StringUtils
import io.reactivex.Observable
import wiki.scene.entity.base.BaseResponse
import wiki.scene.lib_common.provider.router.RouterUtil
import wiki.scene.lib_network.R
import wiki.scene.lib_network.exception.ApiException
import wiki.scene.lib_network.exception.DataNullException
import wiki.scene.lib_network.exception.NetException
import wiki.scene.lib_network.exception.UnAuthorizedException
import wiki.scene.lib_network.ext.changeNew2MainThread

object ApiTransform {
    fun <T> transform(observable: Observable<BaseResponse<T>>): Observable<T> {
        return observable.changeNew2MainThread()
            .flatMap { result ->
                when (result.errorCode) {
                    NetException.ERROR.SUCCESS -> {
                        return@flatMap if (result.data == null) {
                            Observable.error(NetException.handleException(DataNullException(result.errorMsg)))
                        } else {
                            Observable.just(result.data!!)
                        }
                    }
                    NetException.ERROR.UNAUTHORIZED -> {
                        //可以在这执行登陆事件
                        toLogin()
                        return@flatMap Observable.error(
                            NetException.handleException(
                                UnAuthorizedException()
                            )
                        )
                    }
                    else -> {
                        return@flatMap if (result.errorMsg.isEmpty()) {
                            Observable.error(
                                NetException.handleException(
                                    ApiException(
                                        StringUtils.getString(
                                            R.string.lib_network_unknown_exception
                                        )
                                    )
                                )
                            )
                        } else {
                            Observable.error(NetException.handleException(ApiException(result.errorMsg)))
                        }
                    }
                }

            }
    }

    private fun toLogin() {
        RouterUtil.launchLogin()
    }
}