package edu.ucne.abrahamelhage_ap2_p1.presentation.Screen.Servicio

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import edu.ucne.abrahamelhage_ap2_p1.ui.theme.AbrahamElHage_AP2_P1Theme

@Composable
fun ServicioScreen(
    viewModel: ServicioViewModel,
    navController: NavController,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ServicioBody(
        uiState = uiState,
        limpiarServicio = viewModel::limpiarServicio,
        onVolver = {
            navController.popBackStack()
        },
        onSaveServicio = {
            viewModel.saveServicio()
        },
        onDeleteServicio = viewModel::deleteServicio,
        onPrecioChanged = viewModel::onPrecioChanged,
        onDescripcionChanged = viewModel::onDescripcionChanged
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicioBody(
    uiState: ServicioUIState,
    limpiarServicio: () -> Unit,
    onVolver: () -> Unit,
    onSaveServicio: () -> Boolean,
    onDeleteServicio: () -> Unit,
    onPrecioChanged: (String) -> Unit,
    onDescripcionChanged: (String) -> Unit,
) {
    val context = LocalContext.current
    var showDeleteModeDialog by remember { mutableStateOf(false) }

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
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {


            OutlinedTextField(
                label = { Text(text = "Descripción") },
                value = uiState.descripcion,
                onValueChange = { onDescripcionChanged(it) },
                isError = !uiState.descripcionError.isNullOrEmpty(),
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                )
            )

            if (!uiState.descripcionError.isNullOrEmpty()) {
                Text(text = uiState.descripcionError ?: "", color = Color.Red)
            }

            OutlinedTextField(
                label = { Text(text = "Precio") },
                value = uiState.precio.toString(),
                onValueChange = { onPrecioChanged(it) },
                isError = !uiState.precioError.isNullOrEmpty(),
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )

            if (!uiState.precioError.isNullOrEmpty()) {
                Text(text = uiState.precioError ?: "", color = Color.Red)
            }

            Spacer(modifier = Modifier.padding(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                if (uiState.servicioId != 0 && uiState.servicioId != null) {
                    OutlinedButton(
                        onClick = {
                            showDeleteModeDialog = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "delete button"
                        )
                        Text(text = "Eliminar")
                    }
                }
                OutlinedButton(
                    onClick = {
                        limpiarServicio()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "new button"
                    )
                    Text(text = "Nuevo")
                }
                OutlinedButton(
                    onClick = {
                        var id = uiState.servicioId

                        if (onSaveServicio()) {
                            if (id != 0 && id != null) {

                                Toast.makeText(
                                    context,
                                    "Servicio actualizado",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            } else {
                                Toast.makeText(context, "Servicio agregado", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            onVolver()
                        }
                    }
                ) {
                    if (uiState.servicioId == 0 || uiState.servicioId == null) {
                        Text(text = "Guardar")
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "save button"
                        )
                    } else {
                        Text(text = "Actualizar")
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "save button"
                        )
                    }
                }
            }
        }
        if (showDeleteModeDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteModeDialog = false },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Warning",
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            "Eliminar",
                            style = typography.titleMedium
                        )
                    }
                },
                text = {
                    Text(
                        "¿Esta seguro que desea eliminar el servicio?",
                        style = typography.bodyMedium
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDeleteModeDialog = false
                            onDeleteServicio()
                            Toast.makeText(context, "Servicio eliminado", Toast.LENGTH_SHORT)
                                .show()
                            onVolver()
                        },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = Color.Red
                        )
                    ) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDeleteModeDialog = false }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewServicioBody() {
    AbrahamElHage_AP2_P1Theme {
        ServicioBody(
            uiState = ServicioUIState(),
            limpiarServicio = {},
            onVolver = {},
            onSaveServicio = { true },
            onDeleteServicio = {},
            onPrecioChanged = {},
            onDescripcionChanged = {}
        )
    }
}
