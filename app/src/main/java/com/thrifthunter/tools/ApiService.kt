package com.thrifthunter.settings

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

data class RegisterResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class LoginResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("loginResult")
    val loginResult: LoginResultResponse
)

data class LoginResultResponse(
    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("token")
    val token: String
)

data class GetResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("listStory")
    val listStory: List<ListStory>
)

data class ListStory(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("photoUrl")
    val photoUrl: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("lat")
    val lat: Double,

    @field:SerializedName("lon")
    val lon: Double
)

data class FileUploadResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<LoginResponse>

    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Header("Authorization") token: String
    ): GetResponse

    @Multipart
    @POST("stories")
    fun uploadStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Header("Authorization") token: String
    ) : Call<FileUploadResponse>

    @GET("stories?location=1")
    fun getMarkers(
        @Header("Authorization") token: String
    ) : Call<GetResponse>
}