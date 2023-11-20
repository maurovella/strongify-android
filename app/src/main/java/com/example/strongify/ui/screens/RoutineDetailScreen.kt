package com.example.strongify.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.strongify.MainViewModel
import com.example.strongify.R
import com.example.strongify.ui.components.ExerciseCard
import com.example.strongify.util.getViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@Composable
fun RoutineDetailScreen(
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
    routineId: Int,
    navController: NavController
) {
    val cycleIdx = remember { mutableIntStateOf(0) }
    val review = remember { mutableStateOf(false) }
    val exIdx = remember { mutableIntStateOf(0) }
    val fondo = Color(0xFF1C2120)
    val execution = remember { mutableStateOf(false) }
    var dropdown by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "https://www.strongify.com/routine/list/${routineId}")
        type = "text/plain"
    }

    LaunchedEffect(key1 = true) {
        viewModel.getRoutine(routineId = routineId)
    }

    if (viewModel.uiState.currentRoutine != null && viewModel.uiState.cycleDataList.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(fondo),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row( modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 8.dp),
                verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    if(!navController.popBackStack())
                        navController.navigate(Screen.HomeScreenClass.route)
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                IconButton(onClick = {
                    context.startActivity(Intent.createChooser(sendIntent, null))
                }) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
            Text(
                text = viewModel.uiState.currentRoutine!!.name.uppercase(),
                color = Color.Red,
                fontSize = 32.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleName,
                color = Color.Red,
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(7.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
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
                                text = stringResource(id = R.string.list),
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
                                    text = { Text(text = stringResource(id = R.string.sequential)) },
                                    onClick = {
                                        navController.navigate(Screen.RoutineScreenClass.route + "/sequential" + "/$routineId")
                                    }
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                if (!execution.value) {
                    Button(
                        //colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                        onClick = {
                            execution.value = !execution.value
                            exIdx.intValue = 0
                            cycleIdx.intValue = 0
                        }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text(text = stringResource(id = R.string.start_routine))
                    }
                }
                else {
                    Button(
                        //colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                        onClick = {
                            review.value = true
                        }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text(text = stringResource(id = R.string.finish))
                    }
                }
            }
            LazyColumn {
                val cycles = viewModel.uiState.cycleDataList

                itemsIndexed(cycles) { index, cycle ->
                    // Render cycle information
                    Text(
                        text = cycle.cycleName,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontFamily = FontFamily.SansSerif, // Cambia a la fuente que desees
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp)
                    )

                    // Render exercises for the current cycle
                    val cycleExercises = cycle.cycleExercises
                    for (exerciseIndex in cycleExercises.indices) {
                        val exercise = cycleExercises[exerciseIndex]
                        Column() {
                            Row() {
                                ExerciseCard(
                                    name = exercise.exercise.name,
                                    repetitions = exercise.repetitions!!,
                                    series = cycle.cycleRepetitions
                                )

                            }
                            val current_serie = remember { mutableIntStateOf(1) }
                            if(execution.value) {
                                Row() {
                                    Text(
                                        text = "Serie actual:  " + current_serie.intValue,
                                        color = Color.LightGray,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp,
                                        modifier = Modifier.padding(start = 8.dp, top = 10.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Button(onClick = {
                                        if(current_serie.intValue > 1) {
                                            current_serie.intValue--
                                        }
                                    },colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                                        Text(text = "-", fontSize = 16.sp)
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Button(onClick = {
                                        if(current_serie.intValue < cycle.cycleRepetitions) {
                                            current_serie.intValue++
                                        }
                                    },colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                                        Text(text = "+", fontSize = 16.sp)
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            if (index == cycles.size - 1 && exerciseIndex == cycleExercises.size - 1) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .background(fondo)
                                )
                            }
                        }
                    }
                }
            }
            if (review.value) {
                RateDialog( modifier = Modifier
                    .width(300.dp) // Ancho del diálogo
                    .height(400.dp),
                    onConfirm = {
                        // Acciones al confirmar el diálogo
                        // Ejemplo: enviar valor o ejecutar alguna acción
                        review.value = false // Cierra el diálogo
                        execution.value = !execution.value
                    },
                    onCancel = {
                        // Acciones al cancelar el diálogo
                        review.value = false // Cierra el diálogo
                    },
                    viewModel,
                    routineId
                ) // Alto del diálogo)
            }
        }
    } else {
        // Loading or empty state UI
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator() // or some other indication of loading or empty state
        }
    }
}