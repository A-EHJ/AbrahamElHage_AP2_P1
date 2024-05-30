package edu.ucne.abrahamelhage_ap2_p1.presentation.navigation

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    object List : Screen()
    @Serializable
    data class Body(val Id: Int) : Screen()
}

