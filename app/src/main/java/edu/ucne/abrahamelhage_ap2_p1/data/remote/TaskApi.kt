package com.ucne.myapplication.data.remote

import com.ucne.myapplication.data.remote.dto.TaskDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface TaskApi {
    @GET("api/v1/Activities")
    suspend fun getTasks(): List<TaskDto>

    @GET("api/v1/Activities/{id}")
    suspend fun getTask(@Path("id") id: Int): TaskDto

    @POST("api/v1/Activities")
    suspend fun addTask(@Body task: TaskDto?): TaskDto

    @PUT ("api/v1/Activities/{id}")
    suspend fun updateTask(@Path("id") id: Int, @Body task: TaskDto?): TaskDto

    @DELETE("api/v1/Activities/{id}")
    suspend fun deleteTask(@Path("id") id: Int): Response<Unit>
}

