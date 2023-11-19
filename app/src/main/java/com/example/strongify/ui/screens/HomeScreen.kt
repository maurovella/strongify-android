package com.example.strongify.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
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
import com.example.strongify.MainUiState
import com.example.strongify.MainViewModel
import com.example.strongify.R
import com.example.strongify.ui.components.RoutineCard
import com.example.strongify.util.getViewModelFactory


@Composable
fun HomeScreen(
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
    navToRoutineDetail: (Int) -> Unit,
    isPhone: Boolean = true
) {
    val uiState = viewModel.uiState
    Surface {
        if (isPhone) {
            PhoneLayout(uiState = uiState, viewModel = viewModel, navToRoutineDetail)
        } else {
            TabletLayout(uiState = uiState, viewModel = viewModel, navToRoutineDetail)
        }
    }
}

@Composable
private fun PhoneLayout(uiState: MainUiState, viewModel: MainViewModel, navToRoutineDetail: (Int) -> Unit) {
    val fondo = Color(0xFF1C2120)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(1f)
    ) {
        LaunchedEffect(key1 = true) {
            viewModel.getRoutines() // Llama a getRoutines solo una vez cuando se carga la pantalla
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            content = {
                //viewModel.getRoutines()
                val routines = viewModel.uiState.routines
                val favorites = viewModel.uiState.favorites
                if (!routines.isNullOrEmpty()) {
                    itemsIndexed(routines) { index, routine ->
                        val isFaved = favorites?.any { it.id == routine.id } ?: false
                        RoutineCard(
                            viewModel,
                            routine,
                            isFaved = isFaved,
                            modifier = Modifier.clickable(onClick = { navToRoutineDetail(routine.id) }),
                            func = navToRoutineDetail
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
                            fontSize = 20.sp,
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

@Composable
private fun TabletLayout(uiState: MainUiState, viewModel: MainViewModel, navToRoutineDetail: (Int) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(1f)
    ) {
        Text(
            text = stringResource(R.string.nav_home),
            fontSize = 40.sp
        )
        ElevatedButton(
            onClick = {
                viewModel.getCurrentUser()
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Button for Tablet")
        }
        val currentUserData = uiState.currentUser?.let {
            "Current User: ${it.firstName} ${it.lastName} (${it.email})"
        }
        Text(
            text = currentUserData ?: "NO HAY USUARIO",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            fontSize = 24.sp
        )
    }
}
