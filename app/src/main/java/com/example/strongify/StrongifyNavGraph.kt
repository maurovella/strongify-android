package com.example.strongify

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.strongify.ui.screens.FavoriteScreen
import com.example.strongify.ui.screens.HomeScreen
import com.example.strongify.ui.screens.RoutineScreen
import com.example.strongify.ui.screens.Screen

@Composable
fun StrongifyNavGraph(
    navController: NavHostController = rememberNavController()
) {
    val uri = "http://www.strongify.com"
    val secureUri = "https://www.strongify.com"
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreenClass.route
    ) {
        composable(Screen.HomeScreenClass.route) {
            HomeScreen()
        }
        composable(Screen.RoutineScreenClass.route) {
            RoutineScreen()
        }
        composable(Screen.FavoriteScreenClass.route) {
            FavoriteScreen()
        }
    }
}