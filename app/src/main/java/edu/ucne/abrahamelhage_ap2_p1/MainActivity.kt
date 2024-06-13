package edu.ucne.abrahamelhage_ap2_p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.abrahamelhage_ap2_p1.data.local.database.ServicioDb
import edu.ucne.abrahamelhage_ap2_p1.data.repository.ServicioRepository
import edu.ucne.abrahamelhage_ap2_p1.presentation.navigation.Parcial1NavHost
import edu.ucne.abrahamelhage_ap2_p1.ui.theme.AbrahamElHage_AP2_P1Theme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            AbrahamElHage_AP2_P1Theme {
                val navController = rememberNavController()
                Parcial1NavHost(navHostController = navController)
            }
        }
    }
}

