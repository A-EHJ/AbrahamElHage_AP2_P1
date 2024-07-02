package edu.ucne.abrahamelhage_ap2_p1.presentation.screen.task


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucne.myapplication.data.remote.dto.TaskDto
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.abrahamelhage_ap2_p1.data.repository.Resource
import edu.ucne.abrahamelhage_ap2_p1.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) :
    ViewModel() {
    private val taskId: Int = 0

    private val _uiState = MutableStateFlow(TaskUIState())
    val uiState = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            getTasks()
        }
    }

    fun getTasks() {
        taskRepository.getTasks().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.update {
                        it.copy(isLoading = true)
                    }
                }

                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            tasks = result.data ?: emptyList()
                        )
                    }
                }

                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message ?: "An unexpected error occurred"
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun deletateTask(taskId: Int) {
        viewModelScope.launch {
            taskRepository.deleteTask(taskId)
        }
    }

    fun addTask() {
        viewModelScope.launch {
            taskRepository.addTask(uiState.value.toEntity())
        }
    }

    fun updateTask(task: TaskDto) {
        viewModelScope.launch {
            taskRepository.updateTask(uiState.value.toEntity())
        }
    }

    fun newTask() {
        viewModelScope.launch {
            _uiState.value = TaskUIState()
        }
    }

    fun getTask(taskId: Int) {
        viewModelScope.launch {
            val task = taskRepository.getTask(taskId)
            task?.let {
                _uiState.update {
                    it.copy(
                        id = task.id,
                        title = task.title,
                        dueDate = task.dueDate.substring(0, 10),
                        completed = task.completed
                    )
                }
            }
        }
    }

    fun onTitleChanged(title: String) {
        _uiState.update {
            it.copy(
                title = title,
                errorTitle = ""
            )
        }
    }

    fun onDueDateChanged(dueDate: String) {
        _uiState.update {
            it.copy(
                dueDate = dueDate,
                errorDueDate = ""
            )
        }
    }

    fun onCompletedChanged() {
        var completed = _uiState.value.completed
        completed = !completed!!
        _uiState.update {
            it.copy(
                completed = completed,
                errorCompleted = ""
            )
        }
    }

    fun validation(): Boolean {
        val titleEmpty = uiState.value.title.isNullOrEmpty()
        val dueDateEmpty = uiState.value.dueDate.isNullOrEmpty()
        if (titleEmpty) {
            _uiState.update {
                it.copy(
                    errorTitle = "Campo Obligatorio"
                )
            }
        }
        if (dueDateEmpty) {
            _uiState.update {
                it.copy(
                    errorDueDate = "Campo Obligatorio"
                )
            }
        }
        return !titleEmpty && !dueDateEmpty
    }

}


data class TaskUIState(
    val id: Int? = null,
    var title: String? = "",
    var errorTitle: String? = "",
    var dueDate: String? = "",
    var errorDueDate: String? = "",
    var completed: Boolean? = false,
    var errorCompleted: String? = "",
    val isLoading: Boolean = false,
    val tasks: List<TaskDto> = emptyList(),
    val errorMessage: String = "",
)

fun TaskUIState.toEntity(): TaskDto {
    return TaskDto(
        id ?: 0,
        title ?: "",
        dueDate ?: "",
        completed ?: false
    )
}

