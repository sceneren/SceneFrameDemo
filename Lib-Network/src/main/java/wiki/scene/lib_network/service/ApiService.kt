package wiki.scene.lib_network.service

import androidx.lifecycle.LiveData
import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET
    operator fun get(@Url url: String, @QueryMap map: Map<String, Any>): Observable<String>

    @FormUrlEncoded
    @POST
    fun post(@Url url: String, @FieldMap map: Map<String, Any>): Observable<String>

    @GET
    fun getWithLiveData(@Url url: String, @QueryMap map: Map<String, Any>): LiveData<String>

    @Multipart
    @POST
    fun uploadOneFile(@Url url: String, @Part partList: List<Part>): Observable<String>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST
    fun postWithJson(@Url url: String, @Body body: RequestBody): Observable<String>

    @FormUrlEncoded
    @POST("/api/user/getUserInfo")
    fun getUserInfo(
        @Field("id") id: Int,
        @Field("friend_id") friend_id: Int,
        @Field("token") token: String
    ): Call<ResponseBody>

    @POST
    fun getBanner(@Url url: String): Observable<String>
}