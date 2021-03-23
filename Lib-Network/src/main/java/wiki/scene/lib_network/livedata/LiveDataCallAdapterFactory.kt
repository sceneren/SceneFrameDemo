package wiki.scene.lib_network.livedata

import android.util.Log
import androidx.lifecycle.LiveData
import retrofit2.CallAdapter
import retrofit2.Retrofit
import wiki.scene.lib_network.bean.ApiResponse
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @date: 2020\7\24 0024
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
class LiveDataCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != LiveData::class.java) {
            return null
        }
        //获取第一个泛型类型
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawType = getRawType(observableType)
        Log.e("TAG", "rawType = $rawType")
        var isApiResponse = true
        if (rawType != ApiResponse::class.java) {
            //不是返回ApiResponse类型的返回值
            isApiResponse = false
        }
        if (observableType !is ParameterizedType) {
            Log.e("TAG", "rawType = resource must be parameterized$rawType")
            //            throw new IllegalArgumentException("resource must be parameterized");
        }
        return LiveDataCallAdapter<String>(observableType, isApiResponse)
    }
}