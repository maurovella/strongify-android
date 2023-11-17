package com.example.strongify.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.strongify.MainViewModel
import com.example.strongify.R
import com.example.strongify.data.model.Routine
import com.example.strongify.util.getViewModelFactory

//@Preview
@Composable
fun SecuentialRoutineScreen(viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
routineId: Int) {
    val cycleIdx = remember { mutableIntStateOf(0) }
    val exIdx = remember { mutableIntStateOf(0) }
    var dropdown by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current.applicationContext
    LaunchedEffect(key1 = true) {
        viewModel.getRoutine(routineId = routineId)
    }
    if(viewModel.uiState.currentRoutine != null && viewModel.uiState.cycleDataList.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "",
                    tint = Color.White,
                )
            }
            Text(text = viewModel.uiState.currentRoutine!!.name, color = Color.Red)
            Text(text = viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleName, color = Color.Red)
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.Red)
                        .height(60.dp)
                        .width(100.dp)
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
                                text = stringResource(id = R.string.sequential),
                                color = Color.White
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
                                    text = { Text(text = "hola") },
                                    onClick = {
                                        Toast.makeText(context, "messi", Toast.LENGTH_LONG).show()
                                    }
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(R.drawable.ejercicio_1),
                contentDescription = null,
                modifier = Modifier.size(250.dp)
            )
            Text(text = viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleExercises[exIdx.intValue].exercise.name , color = Color.White)
            Text(text = viewModel.uiState.cycleDataList.size.toString() , color = Color.White)
            Text(text = viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleExercises.size.toString() , color = Color.White)
            Text(text = cycleIdx.intValue.toString() , color = Color.White)
            Text(text = exIdx.intValue.toString() , color = Color.White)
            Text(text = "Repetitions" + viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleExercises[exIdx.intValue].repetitions, color = Color.White)
            Text(text = "Tiempo estimado" + viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleExercises[exIdx.intValue].duration, color = Color.White)
            Text(text = "Series" + viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleRepetitions, color = Color.White)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "-")
                }
                Text(text = "mucho texto", color = Color.White)
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "+")
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if( !( cycleIdx.intValue == 0 && exIdx.intValue == 0 ))
                    Button(onClick = {
                            if (exIdx.intValue == 0) {
                                cycleIdx.intValue--
                                exIdx.intValue = viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleExercises.size -1
                            } else {
                                exIdx.intValue--
                            }
                        }
                    ) {
                        Text(text = stringResource(id = R.string.previous))
                    }
                if( !(viewModel.uiState.cycleDataList.size -1 == cycleIdx.intValue && viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleExercises.size-1 == exIdx.intValue) ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        onClick = {
                            if (viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleExercises.size - 1 == exIdx.intValue) {
                                exIdx.intValue = 0
                                cycleIdx.intValue++
                            } else {
                                exIdx.intValue++
                            }
                        }
                    ) {
                        Text(text = stringResource(id = R.string.next))
                    }
                }
            }
            Button(
                //colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                onClick = { /*TODO*/ }
            ) {
                Text(text = stringResource(id = R.string.finish))
            }
        }
    }
}