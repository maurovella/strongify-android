package com.example.strongify.ui.screens


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.strongify.MainViewModel
import com.example.strongify.R
import com.example.strongify.ui.components.RoutineCard
import com.example.strongify.util.getViewModelFactory

@Composable
fun FavoriteScreen(
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
    navController: NavController,
    isPhone: Boolean = true
) {
    val fondo = Color(0xFF1C2120)
    val fontSize = if (isPhone) 30.sp else 40.sp // Define different font sizes for phone and tablet
    Surface(color = fondo) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.nav_favorite),
                    fontSize = fontSize,
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            LaunchedEffect(key1 = true) {
                viewModel.getFavorites() // Llama a getRoutines solo una vez cuando se carga la pantalla
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                content = {
                    //viewModel.getRoutines()
                    val favorites = viewModel.uiState.favorites
                    if(!favorites.isNullOrEmpty()){
                        itemsIndexed(favorites) { _, routine ->
                            RoutineCard(
                                viewModel,
                                routine,
                                isFaved = true,
                                modifier = Modifier.clickable(onClick = { navController.navigate(Screen.RoutineScreenClass.route + "/sequential" + "/${routine.id}") }),
                                navController = navController,
                                isPhone = isPhone
                            )
                        }
                    } else {
                        item {
                            // Mensaje a mostrar cuando no hay favoritos
                            Text(
                                text = stringResource(id = R.string.no_favs),
                                color = Color.White,
                                fontSize = fontSize,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )
                        }
                    }
                }
            )
        }
    }
}
