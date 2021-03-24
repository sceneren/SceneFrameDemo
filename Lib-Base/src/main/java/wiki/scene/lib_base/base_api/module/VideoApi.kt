package wiki.scene.lib_base.base_api.module

import androidx.lifecycle.LiveData
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import wiki.scene.lib_network.bean.ApiResponse

interface VideoApi {
    @POST("channel/get_info")
    fun getChannelInfo(): LiveData<ApiResponse<String>>

    @POST("search/recommend")
    @FormUrlEncoded
    fun searchRecommend(): LiveData<ApiResponse<String>>

    @POST("search/result")
    @FormUrlEncoded
    fun searchResult(@Field("kw") kw: String): LiveData<ApiResponse<String>>

}