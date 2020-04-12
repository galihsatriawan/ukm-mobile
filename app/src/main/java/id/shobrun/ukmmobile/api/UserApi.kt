package id.shobrun.ukmmobile.api

import androidx.lifecycle.LiveData
import id.shobrun.ukmmobile.models.entity.User
import id.shobrun.ukmmobile.models.network.ProfileResponse
import id.shobrun.ukmmobile.models.network.UsersResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserApi {
    @POST("user/login")
    fun loginUser(@Body data: HashMap<String, String>): LiveData<ApiResponse<ProfileResponse>>

    @POST("user/register")
    fun registerUser(@Body data: HashMap<String, Any?>): LiveData<ApiResponse<UsersResponse>>

    @POST("user/detail")
    @FormUrlEncoded
    fun getDetailUser(@Field("username") username: String): LiveData<ApiResponse<UsersResponse>>

    @POST("user/edit")
    fun editUser(@Body data: HashMap<String, User>): LiveData<ApiResponse<UsersResponse>>
}