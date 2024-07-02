package edu.ucne.abrahamelhage_ap2_p1.presentation.Screen.task

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ucne.myapplication.data.remote.dto.TaskDto
import edu.ucne.abrahamelhage_ap2_p1.presentation.screen.task.TaskUIState
import edu.ucne.abrahamelhage_ap2_p1.presentation.screen.task.TaskViewModel
import edu.ucne.abrahamelhage_ap2_p1.ui.theme.AbrahamElHage_AP2_P1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    viewModel: TaskViewModel = hiltViewModel(),
    onVerTask: (TaskDto) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Task List") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onVerTask(TaskDto(0, "", "", false))
                },
                content = {
                    Text(text = "Add")
                }
            )
        }
    ) { innerpaddig ->
        TaskBody(innerpaddig, viewModel, uiState, onVerTask)
    }
}

@Composable
private fun TaskBody(
    innerpaddig: PaddingValues,
    viewModel: TaskViewModel,
    uiState: TaskUIState,
    onVerTask: (TaskDto) -> Unit
) {
    Spacer(modifier = Modifier.height(32.dp))
    Box(modifier = Modifier.fillMaxSize().padding(innerpaddig)) {
        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center)) // Center the indicator
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        TextButton(
            onClick = {
                viewModel.getTasks()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Get Tasks")
        }

        if (uiState.errorMessage.isNotEmpty()) {
            Text(
                text = uiState.errorMessage,
                color = Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.tasks) { task ->
                TaskItem(task, onVerTask)
            }
        }
    }
}


@Composable
fun TaskItem(task: TaskDto, onVerTask: (TaskDto) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Color.Gray)
            .padding(8.dp)
            .clickable(onClick = {
                onVerTask(task)
            })
    ) {
        Text(text = task.title)
        Text(text = "Due Date: ${task.dueDate.substring(0, 10)}")
        Text(text = "Completed: ${task.completed}")
        Spacer(modifier = Modifier.height(8.dp))
    }
}


@Preview
@Composable
private fun PreviewServicioBody() {
    val tasks = listOf(
        TaskDto(1, "Task 1", "2023-12-15", false),
        TaskDto(2, "Task 2", "2023-12-20", true)
    )
    AbrahamElHage_AP2_P1Theme {
        val viewModel: TaskViewModel = hiltViewModel() // Use hiltViewModel()
        TaskBody(
            innerpaddig = PaddingValues(16.dp),
            viewModel = viewModel,
            uiState = TaskUIState(tasks = tasks),
            onVerTask = {}
        )

    }
}

