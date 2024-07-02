package edu.ucne.abrahamelhage_ap2_p1.data.repository

import com.ucne.myapplication.data.remote.TaskApi
import com.ucne.myapplication.data.remote.dto.TaskDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskApi: TaskApi
) {
    fun getTasks(): Flow<Resource<List<TaskDto>>> = flow {
        emit(Resource.Loading())
        try {
            val users = taskApi.getTasks()
            emit(Resource.Success(users))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
    }

    suspend fun getTask(taskId: Int): TaskDto? {
        return try {
            taskApi.getTask(taskId)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun deleteTask(taskId: Int) {
        taskApi.deleteTask(taskId)
    }

    suspend fun addTask(task: TaskDto) {
        taskApi.addTask(task)
    }

    suspend fun updateTask(task: TaskDto) {
        taskApi.updateTask(task.id, task)
    }


}

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}