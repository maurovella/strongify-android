package com.example.strongify.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val title: String,
    val icon: ImageVector,
    val route: String
) {
    object LoginScreenClass: Screen(
        title = "Login",
        icon = Icons.Filled.Info,
        route = "login"
    )
    object RegisterScreenClass: Screen(
        title = "Register",
        icon = Icons.Filled.Info,
        route = "register"
    )
    object HomeScreenClass: Screen(
        title = "Home",
        icon = Icons.Filled.Home,
        route = "home"
    )
    object RoutineScreenClass: Screen(
        title = "Routine",
        icon = Icons.Filled.Build,
        route = "routines"
    )
    object RoutineDetailScreenClass: Screen(
        title = "Routine Detail",
        icon = Icons.Filled.Build,
        route = "routines/list/{routineId}"
    )

    object SecuentialRoutineScreenClass: Screen(
        title = "Secuential Routine",
        icon = Icons.Filled.Build,
        route = "routines/sequential/{routineId}"
    )
    object FavoriteScreenClass: Screen(
        title = "Favorite",
        icon = Icons.Filled.Favorite,
        route = "favorite_screen"
    )
}