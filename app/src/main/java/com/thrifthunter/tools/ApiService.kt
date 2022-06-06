package com.thrifthunter.tools

import com.google.gson.annotations.SerializedName
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
//    @field:SerializedName("userId")
//    val userId: String,

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
    val listItem: List<ListItem>
)

data class ListItem(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("photoUrl")
    val photoUrl: String,

    @field:SerializedName("account")
    val account: String,

    @field:SerializedName("price")
    val price: String,

    @field:SerializedName("category")
    val category: String
)

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("register")
    fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("phone") phone: String,
    ) : Call<RegisterResponse>

    @Headers("Content-Type: application/json")
    @POST("login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<LoginResponse>

    @GET("items")
    suspend fun getStories(
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Header("Authorization") token: String
    ): GetResponse
}