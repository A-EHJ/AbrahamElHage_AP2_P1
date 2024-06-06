package edu.ucne.abrahamelhage_ap2_p1.presentation.Screen.Servicio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucne.abrahamelhage_ap2_p1.data.local.entities.ServicioEntity
import edu.ucne.abrahamelhage_ap2_p1.data.repository.ServicioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ServicioViewModel(
    private val Serviciorepository: ServicioRepository,
    private val ServicioId: Int
) :
    ViewModel() {

    val regexPrecio = Regex("[0-9]{0,7}\\.?[0-9]{0,2}")



    var uiState = MutableStateFlow(ServicioUIState())
        private set

    val servicios = Serviciorepository.getServicios()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )


    fun deleteServicio() {
        viewModelScope.launch {
            Serviciorepository.deleteServicio(uiState.value.toEntity())
        }
    }

    fun onDescripcionChanged(descripcion: String) {
        uiState.update {
            it.copy(descripcion = descripcion)
        }
    }

    fun onPrecioChanged(precio: String) {
        if (precio.matches(regexPrecio)) {
            val precioNuevo = precio.toDouble()
            uiState.update {
                it.copy(
                    precio = precioNuevo
                )
            }
        }
    }

    init {
        viewModelScope.launch {
            val Servicio = Serviciorepository.getServicio(ServicioId)

            Servicio?.let {
                uiState.update {
                    it.copy(
                        servicioId = Servicio.servicioId ?: 0,
                        descripcion = Servicio.descripcion ?: "",
                        precio = Servicio.precio
                    )
                }
            }
        }
    }


    fun saveServicio(): Boolean {

        var valido = true

        uiState.update {
            it.copy(descripcionError = null, precioError = null)

        }

        viewModelScope.launch {

        }

        if (uiState.value.servicioId == 0) {
            uiState.update {
                it.copy(servicioId = null)
            }
        }


        if (uiState.value.descripcion.isEmpty() || uiState.value.descripcion.isBlank() || uiState.value.descripcion == "") {
            uiState.update {
                it.copy(descripcionError = "La descripción no puede estar vacío")
            }
            valido = false
        }

        if (uiState.value.precio == 0.0) {
            uiState.update {
                it.copy(precioError = "El precio no puede ser 0")
            }
            valido = false
        }

        if (!valido) {
            return valido
        }



        viewModelScope.launch {
            /*if (descripcionServicioExiste()) {
                uiState.update {
                    it.copy(descripcionError = "La descripción ya existe")
                }
                valido = false
                return@launch
            }*/

            Serviciorepository.saveServicio(uiState.value.toEntity())
        }

        if (!valido) {
            return false
        } else {
            return true
        }
    }

    fun limpiarServicio() {
        viewModelScope.launch {
            uiState.update {
                it.copy(
                    servicioId = null,
                    descripcion = "",
                    descripcionError = null,
                    precio = 0.0,
                    precioError = null
                )
            }
        }
    }

    private suspend fun descripcionServicioExiste(): Boolean {
        return Serviciorepository.existeServicio(
            uiState.value.descripcion,
            uiState.value.servicioId ?: 0
        )
    }
}


data class ServicioUIState(
    val servicioId: Int? = null,
    var descripcion: String = "",
    var descripcionError: String? = null,
    var precio: Double? = 0.0,
    var precioError: String? = null
)

fun ServicioUIState.toEntity(): ServicioEntity {
    return ServicioEntity(
        servicioId = servicioId,
        descripcion = descripcion,
        precio = precio ?: 0.0
    )
}