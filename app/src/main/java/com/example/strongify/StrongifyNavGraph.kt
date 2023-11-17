package com.example.strongify

import androidx.compose.runtime.Composable
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

@Composable
fun StrongifyNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.LoginScreenClass.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.LoginScreenClass.route) {
            LoginScreen(
                onLogin = { navController.navigate(Screen.HomeScreenClass.route) },
                navToRegister = { navController.navigate(Screen.RegisterScreenClass.route) }
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
                navToRoutineDetail = {route -> navController.navigate(Screen.RoutineScreenClass.route + "/$route") }
            )
        }
        composable(Screen.RoutineDetailScreenClass.route) {
            RoutineDetailScreen(
                routineId = it.arguments?.getString("routineId")?.toInt() ?: 0
            )
        }
        composable(Screen.FavoriteScreenClass.route) {
            FavoriteScreen()
        }
    }
}