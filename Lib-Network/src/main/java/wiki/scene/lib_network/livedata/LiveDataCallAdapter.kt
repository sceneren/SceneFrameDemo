package wiki.scene.lib_network.livedata

import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import wiki.scene.lib_network.bean.ApiResponse
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @date: 2020\7\24 0024
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
class LiveDataCallAdapter<T> internal constructor(
    private val mResponseType: Type,
    private val isApiResponse: Boolean
) : CallAdapter<T, LiveData<T>> {
    override fun responseType(): Type {
        return mResponseType
    }

    override fun adapt(call: Call<T>): LiveData<T> {
        return MyLiveData(call, isApiResponse)
    }

    private class MyLiveData<T> internal constructor(
        private val call: Call<T>,
        private val isApiResponse: Boolean
    ) : LiveData<T>() {
        private val stared = AtomicBoolean(false)
        override fun onActive() {
            super.onActive()
            //确保执行一次
            val b = stared.compareAndSet(false, true)
            if (b) {
                call.enqueue(object : Callback<T> {
                    override fun onResponse(call: Call<T>, response: Response<T>) {
                        val body = response.body()
                        postValue(body)
                    }

                    override fun onFailure(call: Call<T>, t: Throwable) {
                        if (isApiResponse) {
                            postValue(ApiResponse<Any>(ApiResponse.codeError, t.message!!) as T)
                        } else {
                            postValue(t.message as T?)
                        }
                    }
                })
            }
        }
    }
}