package com.thrifthunter.tools

import com.google.gson.annotations.SerializedName
import com.thrifthunter.tools.response.ListItemBarang
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

    @field:SerializedName("values")
    val values: LoginResultResponse
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

    @field:SerializedName("items")
    val items: List<ListItem>
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
    @FormUrlEncoded
    @POST("register")
    fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("phone") phone: String
    ) : Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<LoginResponse>

    @GET("items")
    suspend fun getProduct(
        @Query("keyword") name: String,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("category") category: String,
        @Header("Authorization") token: String
    ): GetResponse


    @GET("items")
     fun getProductItem(
        @Query("keyword") name: String,
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): Call<ListItemBarang>

//    @GET("items?category=tshirt")
//    suspend fun getStoriesTShirt(
//        @Query("page") page: Int?,
//        @Query("size") size: Int?,
//        @Header("Authorization") token: String
//    ): GetResponse
//
//    @GET("items?category=jacket")
//    suspend fun getStoriesJacket(
//        @Query("page") page: Int?,
//        @Query("size") size: Int?,
//        @Header("Authorization") token: String
//    ): GetResponse
//
//    @GET("items?category=shoes")
//    suspend fun getStoriesShoes(
//        @Query("page") page: Int?,
//        @Query("size") size: Int?,
//        @Header("Authorization") token: String
//    ): GetResponse
//
//    @GET("items?category=jeans")
//    suspend fun getStoriesJeans(
//        @Query("page") page: Int?,
//        @Query("size") size: Int?,
//        @Header("Authorization") token: String
//    ): GetResponse
//
////  buat search
//    @GET("items?")
//    suspend fun getSearch(
//        @Q
//        @Query("page") page: Int?,
//        @Query("size") size: Int?,
//        @Header("Authorization") token: String
//    ): GetResponse
}
