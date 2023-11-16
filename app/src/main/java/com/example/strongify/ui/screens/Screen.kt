package com.example.strongify.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val title: String,
    val icon: ImageVector,
    val route: String
) {
    object HomeScreenClass: Screen(
        title = "Home",
        icon = Icons.Filled.Home,
        route = "home_screen"
    )
    object RoutineScreenClass: Screen(
        title = "Routine",
        icon = Icons.Filled.Build,
        route = "routine_screen"
    )

    object FavoriteScreenClass: Screen(
        title = "Favorite",
        icon = Icons.Filled.Favorite,
        route = "favorite_screen"
    )
}