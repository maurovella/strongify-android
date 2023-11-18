package com.example.strongify.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.strongify.MainViewModel
import com.example.strongify.ui.components.CycleCard
import com.example.strongify.ui.components.ExerciseCard
import com.example.strongify.util.getViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@Composable
fun RoutineDetailScreen(
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
    routineId: Int,
    nav: (Int) -> Unit
) {
    val cycleIdx = remember { mutableIntStateOf(0) }
    val exIdx = remember { mutableIntStateOf(0) }
    var dropdown by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current.applicationContext

    LaunchedEffect(key1 = true) {
        viewModel.getRoutine(routineId = routineId)
    }

    if (viewModel.uiState.currentRoutine != null && viewModel.uiState.cycleDataList.isNotEmpty()) {
        Column(
            /*topBar = {
                TopAppBar(
                    title = {
                        Text(text = viewModel.uiState.currentRoutine!!.name, color = Color.White)
                    },
                    navigationIcon = {
                        IconButton(onClick = { /* TODO: Handle back press */ }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "", tint = Color.White)
                        }
                    },
                    actions = {
                        // Assuming you have actions like share or favorite
                        IconButton(onClick = { /* TODO */ }) {
                            Icon(Icons.Default.Share, contentDescription = "Share", tint = Color.White)
                        }
                        IconButton(onClick = { /* TODO */ }) {
                            Icon(Icons.Default.Favorite, contentDescription = "Favorite", tint = Color.White)
                        }
                    }
                )
            }*/
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn {
                val cycles = viewModel.uiState.cycleDataList

                itemsIndexed(cycles) { _, cycle ->
                    // Render cycle information
                    Text(text = "Cycle: ${cycle.cycleName}", color = Color.White)

                    // Render exercises for the current cycle
                    val cycleExercises = cycle.cycleExercises
                    for (exerciseIndex in cycleExercises.indices) {
                        val exercise = cycleExercises[exerciseIndex]
                        ExerciseCard(
                            name = exercise.exercise.name,
                            repetitions = exercise.repetitions!!,
                            series = cycle.cycleRepetitions
                        )
                    }
                }
            }

        }
    } else {
        // Loading or empty state UI
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator() // or some other indication of loading or empty state
        }
    }
}