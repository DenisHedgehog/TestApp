package org.hedgehog.testapp.rest.utils

import org.hedgehog.testapp.rest.models.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by hedgehog on 06.10.17.
 */

interface RestUtils {

    @GET("posts/{id}")
    fun getPost(@Path("id") id: Int): rx.Observable<Response<PostResponse>>

    @GET("comments/{id}")
    fun getComment(@Path("id") id: Int): rx.Observable<Response<CommentResponse>>

    @GET("users/{id}")
    fun getUser(@Path("id") id: Int): rx.Observable<Response<UsersResponse>>

    @GET("photos/{id}")
    fun getPhoto(@Path("id") id: Int): rx.Observable<Response<PhotoResponse>>

    @GET("todos/{id}")
    fun getToDos(@Path("id") id: Int): rx.Observable<Response<ToDosResponse>>

}