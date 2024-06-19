package com.ucne.myapplication.data.remote

import com.ucne.myapplication.data.remote.dto.TaskDto
import retrofit2.http.GET



interface TaskApi {
    @GET("api/v1/Activities")
    suspend fun getTask(): List<TaskDto>
}

