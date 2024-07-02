package edu.ucne.abrahamelhage_ap2_p1.presentation.Screen.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.abrahamelhage_ap2_p1.presentation.screen.task.TaskUIState
import edu.ucne.abrahamelhage_ap2_p1.presentation.screen.task.TaskViewModel

@Composable
fun TaskScreen(
    viewModel: TaskViewModel = hiltViewModel(),
    taskId: Int,
    onVolver: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) {
        viewModel.getTask(taskId)
    }

    Spacer(modifier = Modifier.height(80.dp))
    Text(text = taskId.toString())
    TaskBody(
        uiState = uiState,
        viewModel = viewModel,
        onVolver = onVolver,
        onTitleChange = { viewModel.onTitleChanged(it) },
        onDueDateChange = { viewModel.onDueDateChanged(it) },
        onCompletedChange = { viewModel.onCompletedChanged() },
        onGuardar = { viewModel.addTask() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskBody(
    uiState: TaskUIState,
    viewModel: TaskViewModel,
    onVolver: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDueDateChange: (String) -> Unit,
    onCompletedChange: () -> Unit,
    onGuardar: () -> Unit
    // onTaskChange: (TaskDto) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Registro Servicio") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Lista"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {

            Text(text = "Servicio")

            OutlinedTextField(
                value = uiState.title ?: "",
                onValueChange = { onTitleChange(it) },
                label = { Text("Servicio") },
                modifier = Modifier.fillMaxWidth()
            )

            uiState.errorTitle?.let {
                Text(
                    text = it,
                )
            }

            OutlinedTextField(
                value = uiState.dueDate ?: "",
                onValueChange = { onDueDateChange(it) },
                label = { Text("Fecha de Servicio") },
                modifier = Modifier.fillMaxWidth()
            )

            uiState.errorDueDate?.let {
                Text(
                    text = it,
                )
            }

            OutlinedButton(
                onClick = { onCompletedChange() },
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.completed ?: false) {
                    Text("Completado")
                } else {
                    Text("No Completado")
                }
            }

            uiState.errorCompleted?.let {
                Text(
                    text = it,
                )
            }

            OutlinedButton(
                onClick = { onGuardar() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar")
            }

            OutlinedButton(
                onClick = { viewModel.newTask() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Nuevo")
            }

            uiState.id?.let {
                OutlinedButton(
                    onClick = { viewModel.deletateTask(1) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Eliminar")
                }
            }
        }
    }
}