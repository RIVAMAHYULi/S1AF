package com.example.s1af.data.retrofit

import com.example.s1af.data.response.DetailUserResponse
import com.example.s1af.data.response.ItemsItem
import com.example.s1af.data.response.UserGithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: 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")
    fun getUser(@Query("q") username : String): Call<UserGithubResponse>

    @GET("users/{username}")
    @Headers("Authorization: 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")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: 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")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: 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")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>


}