package edu.ucne.abrahamelhage_ap2_p1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Parcial1NavHost(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Screen.List) {
        composable<Screen.List> {
        }
        composable<Screen.Body> {
        }

    }
}