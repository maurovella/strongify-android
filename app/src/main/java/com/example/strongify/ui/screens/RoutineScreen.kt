package com.example.strongify.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
fun RoutineScreen(
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
    navController: NavController,
    isPhone: Boolean = true
) {
    var dropdown by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current.applicationContext
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
                    text = stringResource(id = R.string.nav_routines),
                    fontSize = fontSize,
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ){
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color.Red,
                                shape = RoundedCornerShape(
                                    topStart = 16.dp,
                                    bottomStart = 16.dp,
                                    topEnd = 0.dp,
                                    bottomEnd = 0.dp
                                )
                            )
                            .height(40.dp)
                            .width(120.dp)
                            .clickable { dropdown = true }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = stringResource(id = R.string.orderBy),
                                    color = Color.White,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                                IconButton(
                                    onClick = {
                                        dropdown = true
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.KeyboardArrowDown,
                                        contentDescription = "",
                                        tint = Color.White
                                    )
                                }
                                DropdownMenu(
                                    expanded = dropdown,
                                    onDismissRequest = { dropdown = false })
                                {
                                    DropdownMenuItem(
                                        text = { Text(text = stringResource(id = R.string.date_desc)) },
                                        onClick = {
                                            viewModel.getFilteredRoutines("date", "desc")
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text(text = stringResource(id = R.string.date_asc)) },
                                        onClick = {
                                            viewModel.getFilteredRoutines("date", "asc")
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text(text = stringResource(id = R.string.score_desc)) },
                                        onClick = {
                                            viewModel.getFilteredRoutines("score", "desc")
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text(text = stringResource(id = R.string.score_asc)) },
                                        onClick = {
                                            viewModel.getFilteredRoutines("score", "asc")
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text(text = stringResource(id = R.string.diff_desc)) },
                                        onClick = {
                                            viewModel.getFilteredRoutines("difficulty", "desc")
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text(text = stringResource(id = R.string.diff_asc)) },
                                        onClick = {
                                            viewModel.getFilteredRoutines("difficulty", "asc")
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            LaunchedEffect(key1 = true) {
                viewModel.getFavorites()
                viewModel.getRoutines() // Llama a getRoutines solo una vez cuando se carga la pantalla
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                content = {
                    //viewModel.getRoutines()
                    val routines = viewModel.uiState.routines
                    val favorites = viewModel.uiState.favorites
                    if(!routines.isNullOrEmpty()){
                        itemsIndexed(routines) { index, routine ->
                            val isFaved = favorites?.any { it.id == routine.id } ?: false
                            RoutineCard(
                                viewModel,
                                routine,
                                isFaved = isFaved,
                                modifier = Modifier.clickable(onClick = { navController.navigate(Screen.RoutineScreenClass.route + "/sequential" + "/${routine.id}") }),
                                navController = navController,
                                isPhone = isPhone
                            )
                            if (index == routines.size - 1) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .background(fondo)
                                )
                            }
                        }

                    } else {
                        item {
                            // Mensaje a mostrar cuando no hay favoritos
                            Text(
                                text = stringResource(id = R.string.no_routines),
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