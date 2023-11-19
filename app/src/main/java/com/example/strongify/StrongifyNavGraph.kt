package com.example.strongify

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.strongify.ui.screens.FavoriteScreen
import com.example.strongify.ui.screens.HomeScreen
import com.example.strongify.ui.screens.LoginScreen
import com.example.strongify.ui.screens.RegisterScreen
import com.example.strongify.ui.screens.RoutineDetailScreen
import com.example.strongify.ui.screens.RoutineScreen
import com.example.strongify.ui.screens.Screen
import com.example.strongify.ui.screens.SecuentialRoutineScreen
import com.example.strongify.util.getViewModelFactory

@Composable
fun StrongifyNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.LoginScreenClass.route,
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory())
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.LoginScreenClass.route) {
            LoginScreen(
                onLogin = { navController.navigate(Screen.HomeScreenClass.route) },
                navController = navController
                //navToRegister = { navController.navigate(Screen.RegisterScreenClass.route) }
            )
        }
        composable(Screen.RegisterScreenClass.route) {
            RegisterScreen()
        }
        composable(Screen.HomeScreenClass.route) {
            HomeScreen()
        }
        composable(Screen.RoutineScreenClass.route) {
            RoutineScreen(
                navController = navController
            )
        }
        composable(Screen.RoutineDetailScreenClass.route) {
            RoutineDetailScreen(
                routineId = it.arguments?.getString("routineId")?.toInt() ?: 0,
                navController = navController
            )
        }
        composable(Screen.SecuentialRoutineScreenClass.route) {
            SecuentialRoutineScreen(
                routineId = it.arguments?.getString("routineId")?.toInt() ?: 0,
                navController = navController
                //nav = {route -> navController.navigate(Screen.RoutineScreenClass.route + "/list" + "/$route") }
            )
        }
        composable(Screen.FavoriteScreenClass.route) {
            FavoriteScreen(
                navController = navController
            )
        }
    }
}