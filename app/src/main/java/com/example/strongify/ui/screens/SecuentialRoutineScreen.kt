package com.example.strongify.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.strongify.MainViewModel
import com.example.strongify.R
import com.example.strongify.util.getViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SecuentialRoutineScreen(
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
    routineId: Int,
    nav: (Int) -> Unit
) {
    val review = remember { mutableStateOf(false) }
    val execution = remember { mutableStateOf(false) }
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
                                    text = { Text(text = stringResource(id = R.string.list)) },
                                    onClick = {
                                        nav(routineId)
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
            if (!execution.value) {
                Button(
                    //colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                    onClick = {
                        execution.value = !execution.value
                        exIdx.intValue = 0
                        cycleIdx.intValue = 0
                    }
                ) {
                    Text(text = stringResource(id = R.string.start_routine))
                }
            }
            else {
                Button(
                    //colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                    onClick = {
                        review.value = true
                    }
                ) {
                    Text(text = stringResource(id = R.string.finish))
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
    }
}

@Composable
fun RateDialog(
    modifier: Modifier,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    viewModel: MainViewModel,
    routineId: Int
) {
    var score by remember { mutableStateOf("") }
    var reviewText by remember { mutableStateOf("") }

    // Esta función convierte el texto del puntaje en un número
    fun isValidScore(text: String): Boolean {
        return text.toIntOrNull() in 1..10
    }

    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        ),
        content = {
            Box(
                modifier = modifier
                    .padding(16.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .size(200.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.finished_routine),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = score,
                        onValueChange = { newValue ->
                            if (isValidScore(newValue)) {
                                score = newValue
                            }
                        },
                        label = { Text("Puntaje (1-10)") },
                        singleLine = true,
                        isError = !isValidScore(score), // Muestra error si el puntaje no es válido
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Comentario", color = Color.Black)
                    BasicTextField(
                        value = reviewText,
                        onValueChange = { reviewText = it },
                        textStyle = TextStyle(color = Color.Black),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(4.dp)
                            ),
                        maxLines = 5,
                        singleLine = false,
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.padding(vertical = 8.dp)
                            ) {
                                innerTextField()
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                onConfirm()
                                if(score != "")
                                    viewModel.setRate(routineId,score.toInt(),reviewText)
                                      },
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text(text = "Confirmar")
                        }
                        Button(
                            onClick = onCancel
                        ) {
                            Text(text = "Cancelar")
                        }
                    }
                }
            }
        }
    )
}

