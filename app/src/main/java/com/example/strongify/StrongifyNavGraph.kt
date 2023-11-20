package com.example.strongify

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
        composable(
            route = Screen.LoginScreenClass.route
        ) {
            LoginScreen(
                onLogin = { navController.navigate(Screen.HomeScreenClass.route) },
                navController = navController
            )
        }
        composable(
            route = Screen.RegisterScreenClass.route
        ) {
            RegisterScreen()
        }
        composable(
            route = Screen.HomeScreenClass.route
        ) {
            HomeScreen()
        }
        composable(
            route = Screen.RoutineScreenClass.route
        ) {
            RoutineScreen(
                navController = navController
            )
        }
        composable(
            route = Screen.RoutineDetailScreenClass.route,
            arguments = listOf(navArgument("routineId") { type = NavType.IntType })
        ) { NavBackStackEntry ->
            RoutineDetailScreen(
                routineId = NavBackStackEntry.arguments?.getInt("routineId")!!,
                navController = navController
            )
        }
        composable(
            route = Screen.SecuentialRoutineScreenClass.route,
            arguments = listOf(navArgument("routineId") { type = NavType.IntType })
        ) { NavBackStackEntry ->
            SecuentialRoutineScreen(
                routineId = NavBackStackEntry.arguments?.getInt("routineId")!!,
                navController = navController
            )
        }
        composable(
            route = Screen.FavoriteScreenClass.route
        ) {
            FavoriteScreen(
                navController = navController
            )
        }
    }
}