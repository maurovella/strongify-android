package com.example.strongify

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.strongify.ui.screens.Screen
import com.example.strongify.ui.theme.StrongifyTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StrongifyTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                Scaffold(
                    bottomBar = {
                        if (shouldShowBottomBar(navController)) {
                            BottomBar(
                                currentRoute
                            ) { route ->
                                navController.navigate(route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    }
                ) {
                    StrongifyNavGraph(navController = navController)
                }
            }
        }
    }
}

@Composable
fun BottomBar(
    currentRoute: String?,
    onNavigateToRoute: (String) -> Unit
) {
    val items = listOf(
        Screen.HomeScreenClass,
        Screen.RoutineScreenClass,
        Screen.FavoriteScreenClass
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(text = item.title) },
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = { onNavigateToRoute(item.route) }
            )
        }

    }
}

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    SmallTopAppBar(
        title = { Screen.getTitleFromRoute(currentRoute) },
        navigationIcon = { Icons.Filled.ArrowBack },
        actions = { /* Actions composable */ }
    )
}
@Composable
fun shouldShowTopBar(navController: NavController): Boolean {
    val currentDestination = navController.currentBackStackEntry?.destination
    return currentDestination?.route != Screen.HomeScreenClass.route //&&
    //currentDestination?.route != Screen.LoginScreenClass.route &&
    //currentDestination?.route != Screen.RegisterScreenClass.route
}
*/

@Composable
fun shouldShowBottomBar(navController: NavController): Boolean {
    val currentDestination = navController.currentBackStackEntry?.destination
    return  currentDestination?.route != Screen.LoginScreenClass.route &&
            currentDestination?.route != Screen.RegisterScreenClass.route
}
