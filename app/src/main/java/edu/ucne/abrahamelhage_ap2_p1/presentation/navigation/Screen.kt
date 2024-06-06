package edu.ucne.abrahamelhage_ap2_p1.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    object ServicioList : Screen()
    @Serializable
    data class Servicio(val servicioId: Int) : Screen()
}

