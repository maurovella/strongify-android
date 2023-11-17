package com.example.strongify.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
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
import com.example.strongify.MainViewModel
import com.example.strongify.R
import com.example.strongify.ui.components.RoutineCard
import com.example.strongify.util.getViewModelFactory

@Composable
fun RoutineScreen(viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
    navToRoutineDetail: (Int) -> Unit
    ) {
        Surface(color = Color.Black) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.clickable { /* Acción al presionar la flecha */ }
                    )
                    Text(
                        text = stringResource(id = R.string.nav_routines),
                        fontSize = 30.sp,
                        color = Color.White,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                LaunchedEffect(key1 = true) {
                    viewModel.getRoutines() // Llama a getRoutines solo una vez cuando se carga la pantalla
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    content = {
                        //viewModel.getRoutines()
                        val routines = viewModel.uiState.routines
                        if(routines != null){
                            itemsIndexed(routines) { _, routine ->
                                RoutineCard(
                                    category = routine.category.name,
                                    routineName = routine.name,
                                    difficulty = routine.difficulty!!,
                                    modifier = Modifier.clickable(onClick = { navToRoutineDetail(routine.id) })
                                )
                            }
                        }
                    }
                )
            }
        }
    }