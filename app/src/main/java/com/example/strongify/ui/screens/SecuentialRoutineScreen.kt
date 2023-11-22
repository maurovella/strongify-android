package com.example.strongify.ui.screens

import android.content.res.Configuration
import android.content.Intent
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.strongify.MainViewModel
import com.example.strongify.R
import com.example.strongify.ui.components.RateDialog
import com.example.strongify.util.getViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SecuentialRoutineScreen(
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
    routineId: Int,
    navController: NavController,
    isPhone: Boolean = true
) {
    Surface {
        if (isPhone) {
            PhoneLayout(viewModel = viewModel, routineId, navController)
        } else {
            TabletLayout(viewModel = viewModel, routineId, navController)
        }
    }
}


@Composable
private fun PhoneLayout(
    viewModel: MainViewModel,
    routineId: Int,
    navController: NavController
) {
    val review = remember { mutableStateOf(false) }
    val execution = remember { mutableStateOf(false) }
    val cycleIdx = remember { mutableIntStateOf(0) }
    val exIdx = remember { mutableIntStateOf(0) }
    val current_serie = remember { mutableIntStateOf(1) }
    val fondo = Color(0xFF1C2120)
    var dropdown by remember {
        mutableStateOf(false)
    }

    // Share functionality
    val context = LocalContext.current
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "https://www.strongify.com/routine/list/${routineId}")  // always the list view is shared
        type = "text/plain"
    }

    LaunchedEffect(key1 = true) {
        viewModel.getRoutine(routineId = routineId)
    }
    if(viewModel.uiState.currentRoutine != null && viewModel.uiState.cycleDataList.isNotEmpty()) {
        val landscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

        if (!landscape){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(fondo)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(onClick = {
                        if (!navController.popBackStack())
                            navController.navigate(Screen.HomeScreenClass.route)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
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
                                    text = stringResource(id = R.string.sequential),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
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
                                        text = { Text(text = stringResource(id = R.string.list)) },
                                        onClick = {
                                            navController.navigate(Screen.RoutineScreenClass.route + "/list" + "/${routineId}")
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = viewModel.uiState.currentRoutine!!.name.uppercase(), // Convierte el texto a mayúsculas
                    color = Color.Red,
                    fontSize = 32.sp,
                    fontFamily = FontFamily.SansSerif, // Cambia a la fuente que desees
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    text = viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleName,
                    color = Color.Red,
                    fontSize = 20.sp, // Tamaño de la fuente
                    fontWeight = FontWeight.Bold, // Puedes ajustar el peso de la fuente según lo deseado
                    fontFamily = FontFamily.SansSerif // Cambia por la fuente que prefieras, como FontFamily.SansSerif, FontFamily.Cursive, etc.
                )


                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painter = painterResource(R.drawable.ejercicio_1),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(shape = RoundedCornerShape(50.dp))
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleExercises[exIdx.intValue].exercise.name.toUpperCase(), // Convierte el texto a mayúsculas
                    color = Color.White,
                    fontSize = 24.sp,
                    fontFamily = FontFamily.SansSerif, // Cambia a la fuente que desees
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.reps).uppercase(),
                            color = Color.LightGray,
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleExercises[exIdx.intValue].repetitions.toString(),
                            color = Color.LightGray,
                            textAlign = TextAlign.Start
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.est_time).uppercase(),
                            color = Color.LightGray,
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleExercises[exIdx.intValue].duration.toString(),
                            color = Color.LightGray,
                            textAlign = TextAlign.Start
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Series:".uppercase(),
                            color = Color.LightGray,
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleRepetitions.toString(),
                            color = Color.LightGray,
                            textAlign = TextAlign.Start
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                if (execution.value) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.curr_series).uppercase() + current_serie.intValue,
                            color = Color.White,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Button(onClick = {
                            if (current_serie.intValue > 1) {
                                current_serie.intValue--
                            }
                        }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                            Text(text = "-")
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Button(onClick = {
                            if (current_serie.intValue < viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleRepetitions) {
                                current_serie.intValue++
                            }
                        }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                            Text(text = "+")
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CountdownTimer(viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleExercises[exIdx.intValue].duration!!)
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!(cycleIdx.intValue == 0 && exIdx.intValue == 0))
                        Button(
                            onClick = {
                                current_serie.intValue = 1
                                if (exIdx.intValue == 0) {
                                    cycleIdx.intValue--
                                    exIdx.intValue =
                                        viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleExercises.size - 1
                                } else {
                                    exIdx.intValue--
                                }
                            }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text(text = stringResource(id = R.string.previous))
                        }
                    if (!(viewModel.uiState.cycleDataList.size - 1 == cycleIdx.intValue && viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleExercises.size - 1 == exIdx.intValue)) {
                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                            onClick = {
                                current_serie.intValue = 1
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
                Spacer(modifier = Modifier.height(5.dp))
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
                } else {
                    Button(
                        //colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                        onClick = {
                            review.value = true
                        }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text(text = stringResource(id = R.string.finish))
                    }
                }
                if (review.value) {
                    RateDialog(
                        modifier = Modifier
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
                        routineId,
                        isPhone = true
                    ) // Alto del diálogo)
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(fondo)
                    .verticalScroll(rememberScrollState()),
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
                                    text = stringResource(id = R.string.sequential),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
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
                                        text = { Text(text = stringResource(id = R.string.list)) },
                                        onClick = {
                                            navController.navigate(Screen.RoutineScreenClass.route + "/list" + "/${routineId}")
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = viewModel.uiState.currentRoutine!!.name.uppercase(), // Convierte el texto a mayúsculas
                    color = Color.Red,
                    fontSize = 32.sp,
                    fontFamily = FontFamily.SansSerif, // Cambia a la fuente que desees
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    text = viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleName,
                    color = Color.Red,
                    fontSize = 20.sp, // Tamaño de la fuente
                    fontWeight = FontWeight.Bold, // Puedes ajustar el peso de la fuente según lo deseado
                    fontFamily = FontFamily.SansSerif // Cambia por la fuente que prefieras, como FontFamily.SansSerif, FontFamily.Cursive, etc.
                )


                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painter = painterResource(R.drawable.ejercicio_1),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(shape = RoundedCornerShape(50.dp))
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleExercises[exIdx.intValue].exercise.name.toUpperCase(), // Convierte el texto a mayúsculas
                    color = Color.White,
                    fontSize = 24.sp,
                    fontFamily = FontFamily.SansSerif, // Cambia a la fuente que desees
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.reps).uppercase(),
                            color = Color.LightGray,
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleExercises[exIdx.intValue].repetitions.toString(),
                            color = Color.LightGray,
                            textAlign = TextAlign.Start
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.est_time).uppercase(),
                            color = Color.LightGray,
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleExercises[exIdx.intValue].duration.toString(),
                            color = Color.LightGray,
                            textAlign = TextAlign.Start
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Series:".uppercase(),
                            color = Color.LightGray,
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleRepetitions.toString(),
                            color = Color.LightGray,
                            textAlign = TextAlign.Start
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                if(execution.value) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = stringResource(id = R.string.curr_series).uppercase() + current_serie.intValue , color = Color.White, fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Button(onClick = {
                            if(current_serie.intValue > 1) {
                                current_serie.intValue--
                            }
                        },colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                            Text(text = "-")
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Button(onClick = {
                            if(current_serie.intValue < viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleRepetitions) {
                                current_serie.intValue++
                            }
                        },colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                            Text(text = "+")
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CountdownTimer( viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleExercises[exIdx.intValue].duration!!)
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if( !( cycleIdx.intValue == 0 && exIdx.intValue == 0 ))
                        Button(onClick = {
                            current_serie.intValue = 1
                            if (exIdx.intValue == 0) {
                                cycleIdx.intValue--
                                exIdx.intValue = viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleExercises.size -1
                            } else {
                                exIdx.intValue--
                            }
                        }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text(text = stringResource(id = R.string.previous))
                        }
                    if( !(viewModel.uiState.cycleDataList.size -1 == cycleIdx.intValue && viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleExercises.size-1 == exIdx.intValue) ) {
                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                            onClick = {
                                current_serie.intValue = 1
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
                Spacer(modifier = Modifier.height(5.dp))
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
                        routineId,
                        isPhone = true
                    ) // Alto del diálogo)
                }
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
private fun TabletLayout(
    viewModel: MainViewModel,
    routineId: Int,
    navController: NavController
) {
    val review = remember { mutableStateOf(false) }
    val execution = remember { mutableStateOf(false) }
    val cycleIdx = remember { mutableIntStateOf(0) }
    val exIdx = remember { mutableIntStateOf(0) }
    val current_serie = remember { mutableIntStateOf(1) }
    val fondo = Color(0xFF1C2120)
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
                .background(fondo)
                .verticalScroll(rememberScrollState()),
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
                        .height(60.dp)
                        .width(140.dp)
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
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 8.dp),
                                fontSize = 20.sp
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
                                    text = { Text(text = stringResource(id = R.string.list), fontSize = 20.sp) },
                                    onClick = {
                                        navController.navigate(Screen.RoutineScreenClass.route + "/list" +"/${routineId}")
                                    }
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = viewModel.uiState.currentRoutine!!.name.uppercase(), // Convierte el texto a mayúsculas
                color = Color.Red,
                fontSize = 50.sp,
                fontFamily = FontFamily.SansSerif, // Cambia a la fuente que desees
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleName,
                color = Color.Red,
                fontSize = 38.sp, // Tamaño de la fuente
                fontWeight = FontWeight.Bold, // Puedes ajustar el peso de la fuente según lo deseado
                fontFamily = FontFamily.SansSerif // Cambia por la fuente que prefieras, como FontFamily.SansSerif, FontFamily.Cursive, etc.
            )


            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(R.drawable.ejercicio_1),
                contentDescription = null,
                modifier = Modifier
                    .size(400.dp)
                    .clip(shape = RoundedCornerShape(50.dp))
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleExercises[exIdx.intValue].exercise.name.toUpperCase(), // Convierte el texto a mayúsculas
                color = Color.White,
                fontSize = 40.sp,
                fontFamily = FontFamily.SansSerif, // Cambia a la fuente que desees
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.reps).uppercase(),
                        color = Color.LightGray,
                        textAlign = TextAlign.Start,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleExercises[exIdx.intValue].repetitions.toString(),
                        color = Color.LightGray,
                        textAlign = TextAlign.Start,
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.est_time).uppercase(),
                        color = Color.LightGray,
                        textAlign = TextAlign.Start,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleExercises[exIdx.intValue].duration.toString(),
                        color = Color.LightGray,
                        textAlign = TextAlign.Start,
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Series:".uppercase(),
                        color = Color.LightGray,
                        textAlign = TextAlign.Start,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleRepetitions.toString(),
                        color = Color.LightGray,
                        textAlign = TextAlign.Start,
                        fontSize = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            if(execution.value) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(id = R.string.curr_series).uppercase() + current_serie.intValue , color = Color.White, fontSize = 30.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = {
                            if(current_serie.intValue > 1) {
                                current_serie.intValue--
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text(text = "-", fontSize = 25.sp)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(onClick = {
                        if(current_serie.intValue < viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleRepetitions) {
                            current_serie.intValue++
                        }
                    },colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                        Text(text = "+", fontSize = 25.sp)
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CountdownTimer( viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleExercises[exIdx.intValue].duration!!)
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if( !( cycleIdx.intValue == 0 && exIdx.intValue == 0 ))
                    Button(onClick = {
                        current_serie.intValue = 1
                        if (exIdx.intValue == 0) {
                            cycleIdx.intValue--
                            exIdx.intValue = viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleExercises.size -1
                        } else {
                            exIdx.intValue--
                        }
                    }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text(text = stringResource(id = R.string.previous), fontSize = 25.sp)
                    }
                if( !(viewModel.uiState.cycleDataList.size -1 == cycleIdx.intValue && viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleExercises.size-1 == exIdx.intValue) ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        onClick = {
                            current_serie.intValue = 1
                            if (viewModel.uiState.cycleDataList[cycleIdx.intValue].cycleExercises.size - 1 == exIdx.intValue) {
                                exIdx.intValue = 0
                                cycleIdx.intValue++
                            } else {
                                exIdx.intValue++
                            }
                        }
                    ) {
                        Text(text = stringResource(id = R.string.next), fontSize = 25.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            if (!execution.value) {
                Button(
                    //colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                    onClick = {
                        execution.value = !execution.value
                        exIdx.intValue = 0
                        cycleIdx.intValue = 0
                    }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text(text = stringResource(id = R.string.start_routine), fontSize = 25.sp)
                }
            }
            else {
                Button(
                    //colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                    onClick = {
                        review.value = true
                    }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text(text = stringResource(id = R.string.finish), fontSize = 25.sp)
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
                    routineId,
                    isPhone = false
                ) // Alto del diálogo)
            }
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun CountdownTimer(time: Int) {
    var time by remember { mutableStateOf(time*60) }
    var isRunning by remember { mutableStateOf(false) }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            while (time > 0) {
                delay(1000)
                time--
            }
        }
    }

    val minutes = time / 60
    val seconds = time % 60

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "%02d:%02d".format(minutes, seconds), fontSize = 32.sp, color = Color.White)

            Button(
                onClick = { isRunning = !isRunning }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text(text = if (isRunning) "Pausar" else "Iniciar")
            }
        }
    }
}
