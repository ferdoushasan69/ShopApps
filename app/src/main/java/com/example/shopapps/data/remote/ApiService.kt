package com.example.shopapps.data.remote

import com.example.shopapps.data.model.response.LoginResponse
import com.example.shopapps.data.model.response.ProductResponseItem
import com.example.shopapps.data.model.response.UserResponseItem
import com.example.data.util.CATEGORY
import com.example.data.util.PRODUCTS
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
interface ApiService {
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("username") username : String,
        @Field("password") password : String
    ): LoginResponse

    @FormUrlEncoded
    @POST("users")
    suspend fun register(
        @Field("username") username: String,
        @Field("email") email : String,
        @Field("password") password: String
    ) : UserResponseItem

    @GET(PRODUCTS)
    suspend fun getAllProducts(
        @Query("limit") limit : Int,
    ):List<ProductResponseItem>

    @GET(PRODUCTS)
    suspend fun sortProducts(
        @Query("sort") sort : String
    ):List<ProductResponseItem>

    @GET("$PRODUCTS/$CATEGORY/{$CATEGORY}")
    suspend fun getProductsByCategory(
        @Path(CATEGORY) category : String,
        @Query("limit") limit: Int
    ) : List<ProductResponseItem>

    @GET("$PRODUCTS/{id}")
    suspend fun getSingleProducts(
        @Path("id") id : Int
    ): ProductResponseItem
}