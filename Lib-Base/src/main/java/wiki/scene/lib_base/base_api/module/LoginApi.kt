package wiki.scene.lib_base.base_api.module

import androidx.lifecycle.LiveData
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import wiki.scene.lib_base.base_api.res_data.UserInfo
import wiki.scene.lib_network.bean.ApiResponse

/**
 * Copyright (C)
 * FileName: ArticleApi
 * Author: Zlx
 * Email: 1170762202@qq.com
 * Date: 2020/9/17 10:48
 * Description: 体系api
 */
interface LoginApi {
    /**
     * 登录
     *
     * @param username 账号
     * @param password 密码
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): LiveData<ApiResponse<UserInfo>>

    @FormUrlEncoded
    @POST("user/register")
    fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): LiveData<ApiResponse<UserInfo>>

    @GET("user/logout/json")
    fun logout(): LiveData<ApiResponse<*>>
}