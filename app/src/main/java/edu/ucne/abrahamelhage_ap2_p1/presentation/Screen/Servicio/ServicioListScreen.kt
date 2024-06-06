package edu.ucne.abrahamelhage_ap2_p1.presentation.Screen.Servicio

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.abrahamelhage_ap2_p1.data.local.entities.ServicioEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicioListScreen(
    viewModel: ServicioViewModel,
    onVerServicio: (ServicioEntity) -> Unit,
) {
    val Servicios by viewModel.servicios.collectAsStateWithLifecycle()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Lista de Servicios") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onVerServicio(ServicioEntity())
                },
                content = {
                    Text(text = "Agregar")
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .padding(it)
        ) {
            ServicioListBody(
                Servicio = Servicios,
                onVerServicio = onVerServicio
            )
        }
    }
}



@Composable
fun ServicioListBody(
    Servicio: List<ServicioEntity>,
    onVerServicio: (ServicioEntity) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE0E0E0))
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Right

            ) {
                Text(
                    text = "ID",
                    modifier = Modifier
                        .weight(0.10f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Text(
                    text = "Descripción",
                    modifier = Modifier
                        .weight(0.40f)
                        .padding(start = 10.dp)

                )
                Text(
                    text = "Precio",
                    modifier = Modifier
                        .weight(0.40f)
                        .weight(0.40f)
                        .padding(start = 10.dp)
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(Servicio) { servicio ->
                ServicioRow(
                    onVerServicio = onVerServicio,
                    Servicio = servicio
                )
            }
        }
    }
}


@Composable
private fun ServicioRow(
    onVerServicio: (ServicioEntity) -> Unit,
    Servicio: ServicioEntity
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable { onVerServicio(Servicio) }
        .padding(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)

        ) {
            Text(text = Servicio.servicioId.toString(), modifier = Modifier.weight(0.10f))
            Text(
                text = Servicio.descripcion?: "",
                modifier = Modifier.weight(0.50f)
            )
            Text(
                text = Servicio.precio.toString(),
                modifier = Modifier.weight(0.40f)
            )
        }
    }
}