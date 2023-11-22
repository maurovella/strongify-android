package com.example.strongify

import android.content.res.Configuration
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
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
    val uri = "http://www.strongify.com"
    val secureUri = "https://www.strongify.com"

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

        composable(
            route = Screen.LoginScreenClass.route
        ) {
            LoginScreen(
                onLogin = { navController.navigate(Screen.HomeScreenClass.route) },
                navController = navController,
                isPhone = !isTablet()
            )
        }

        composable(
            route = Screen.RegisterScreenClass.route
        ) {
            RegisterScreen(
                isPhone = !isTablet()
            )
        }

        composable(
            route = Screen.HomeScreenClass.route
        ) {
            HomeScreen(
                navToRoutineDetail = {route -> navController.navigate(Screen.RoutineScreenClass.route + "/sequential" + "/$route") },
                isPhone = !isTablet()
            )
        }

        composable(
            route = Screen.RoutineScreenClass.route
        ) {
            RoutineScreen(
                navController = navController,
                isPhone = !isTablet()
            )
        }

        composable(
            route = Screen.RoutineDetailScreenClass.route,
            arguments = listOf(navArgument("routineId") { type = NavType.IntType }),
            deepLinks = listOf(
                navDeepLink { uriPattern = "$uri/routines/list/{routineId}" },
                navDeepLink { uriPattern = "$secureUri/routines/list/{routineId}" }
            )
        ) { NavBackStackEntry ->
            RoutineDetailScreen(
                routineId = NavBackStackEntry.arguments?.getInt("routineId")!!,
                navController = navController,
                isPhone = !isTablet()
            )
        }

        composable(
            route = Screen.SecuentialRoutineScreenClass.route,
            arguments = listOf(navArgument("routineId") { type = NavType.IntType }),
            deepLinks = listOf(
                navDeepLink { uriPattern = "$uri/routines/sequential/{routineId}" },
                navDeepLink { uriPattern = "$secureUri/routines/sequential/{routineId}" }
            )
        ) { NavBackStackEntry ->
            SecuentialRoutineScreen(
                routineId = NavBackStackEntry.arguments?.getInt("routineId")!!,
                navController = navController,
                isPhone = !isTablet()
            )
        }
        composable(
            route = Screen.FavoriteScreenClass.route
        ) {
            FavoriteScreen(
                navController = navController,
                isPhone = !isTablet()
            )
        }
    }
}