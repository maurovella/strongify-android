package com.example.strongify

import android.content.res.Configuration
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
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
        @Composable
        fun isTablet(): Boolean {
            val configuration = LocalConfiguration.current
            return if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {

                configuration.screenWidthDp > 900
            } else {
                configuration.screenWidthDp > 600
            }
        }
        composable(Screen.LoginScreenClass.route) {
            LoginScreen(
                onLogin = { navController.navigate(Screen.HomeScreenClass.route) },
                navToRegister = { navController.navigate(Screen.RegisterScreenClass.route) },
                isPhone = !isTablet()
            )
        }
        composable(Screen.RegisterScreenClass.route) {
            RegisterScreen(isPhone = !isTablet())
        }
        composable(Screen.HomeScreenClass.route) {
            HomeScreen(navToRoutineDetail = {route -> navController.navigate(Screen.RoutineScreenClass.route + "/sequential" + "/$route") }, isPhone = !isTablet())
        }
        composable(Screen.RoutineScreenClass.route) {
            RoutineScreen(
                navToRoutineDetail = {route -> navController.navigate(Screen.RoutineScreenClass.route + "/sequential" + "/$route") },
                isPhone = !isTablet()
            )
        }
        composable(Screen.RoutineDetailScreenClass.route) {
            RoutineDetailScreen(
                routineId = it.arguments?.getString("routineId")?.toInt() ?: 0,
                nav = {route -> navController.navigate(Screen.RoutineScreenClass.route + "/sequential" + "/$route") },
                isPhone = !isTablet()
            )
        }
        composable(Screen.SecuentialRoutineScreenClass.route) {
            SecuentialRoutineScreen(
                routineId = it.arguments?.getString("routineId")?.toInt() ?: 0,
                nav = {route -> navController.navigate(Screen.RoutineScreenClass.route + "/list" + "/$route") },
                isPhone = !isTablet()
            )
        }
        composable(Screen.FavoriteScreenClass.route) {
            FavoriteScreen(
                navToRoutineDetail = {route -> navController.navigate(Screen.RoutineScreenClass.route + "/$route") },
                isPhone = !isTablet()
            )
        }
    }
}