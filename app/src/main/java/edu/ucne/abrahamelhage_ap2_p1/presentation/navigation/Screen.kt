package edu.ucne.abrahamelhage_ap2_p1.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {

    @Serializable
    object TaskList : Screen()

    @Serializable
    data class Task(val taskId: Int) : Screen()
}

