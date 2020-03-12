package com.gaston.claseluisretrofit

import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Gastón Saillén on 12 March 2020
 */
interface JsonAPI {

    @GET("/posts")
    fun getPosts(): Call<List<Post>>

    @POST("/posts")
    fun createPost(@Body post:Post): Call<Post>

    @PUT("/posts/{id}")
    fun putPost(@Path("id") id:Int,@Body post:Post): Call<Post>

    @PATCH("/posts/{id}")
    fun patchPost(@Path("id") id:Int,@Body post:Post): Call<Post>

    @DELETE("/posts/{id}")
    fun deletePost(@Path("id") id:Int): Call<Unit>
}