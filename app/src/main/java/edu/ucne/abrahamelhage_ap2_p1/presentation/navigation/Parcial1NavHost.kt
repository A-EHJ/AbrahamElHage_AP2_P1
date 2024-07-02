package edu.ucne.abrahamelhage_ap2_p1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.abrahamelhage_ap2_p1.presentation.Screen.task.TaskListScreen
import edu.ucne.abrahamelhage_ap2_p1.presentation.Screen.task.TaskScreen

@Composable
fun Parcial1NavHost(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Screen.TaskList) {

        composable<Screen.TaskList> {
            TaskListScreen(
                onVerTask = {
                    navHostController.navigate(Screen.Task(it.id))
                }
            )
        }

        composable<Screen.Task> {
            val args = it.toRoute<Screen.Task>()
            TaskScreen(
                taskId = args.taskId,
                onVolver = { navHostController.popBackStack() }
            )
        }

        /*composable<Screen.Task> {
            TaskScreen(
                viewModel = viewModel(),
            )
        }
*/

    }
}