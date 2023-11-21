package com.example.strongify.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.strongify.MainUiState
import com.example.strongify.MainViewModel
import com.example.strongify.R
import com.example.strongify.data.model.Routine
import com.example.strongify.ui.components.RoutineCard
import com.example.strongify.util.getViewModelFactory
import kotlinx.coroutines.channels.ticker


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
    val currentScore = remember {
        mutableIntStateOf(7)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize(1f)
            .background(fondo),
    ) {
        LaunchedEffect(key1 = true) {
            viewModel.getRoutines() // Llama a getRoutines solo una vez cuando se carga la pantalla
        }
        Text(text = "Rutina recomendada: ", color = Color.White, fontSize = 30.sp)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            content = {
                //viewModel.getRoutines()
                val reviews = viewModel.uiState.reviewList
                val routines = viewModel.uiState.routines
                val favorites = viewModel.uiState.favorites
                var scoreProm = 0.0;
                if(!routines.isNullOrEmpty()) {
                    itemsIndexed(routines) { index, routine ->
                        LaunchedEffect(key1 = true) {
                            viewModel.getReviews(routine.id)
                        }
                    }
                }
                if (reviews.isNotEmpty()) {
                    val hasReviewAboveCurrentScore = reviews.any { it.score > currentScore.intValue }
                    var flag = false
                    if (hasReviewAboveCurrentScore) {
                        itemsIndexed(reviews) { index, review ->
                            if (review.score > currentScore.intValue) {
                                routines!!.forEach{ routine->
                                    val isFaved = favorites?.any { it.id == routine.id } ?: false
                                    if(routine.id == review.routineId && flag == false){
                                        flag = true
                                        RoutineCard(viewModel,routine = routine, func = navToRoutineDetail , isFaved = isFaved, modifier = Modifier.clickable(onClick = { navToRoutineDetail(routine.id) }), isPhone = true)
                                    }
                                }
                                if(review.review != "") {
                                    Box(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .background(
                                                color = Color.Gray,
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                            .padding(12.dp)
                                    ) {
                                        Column {
                                            Text(
                                                text = "Puntuacion valorada por el usuario: " + review.score.toString(),
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text(
                                                text = review.review,
                                                color = Color.White,
                                                fontSize = 14.sp
                                            )
                                        }
                                    }
                                }
                            }
                            if (index == reviews.size - 1) {
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
                            // Mostrar mensaje cuando ninguna revisión tenga una puntuación mayor a currentScore.intValue
                            NoReviewsScreen()
                        }
                    }
                } else {
                    item {
                        // Mostrar mensaje cuando no haya revisiones en absoluto
                        NoReviewsScreen()
                    }
                }
            }
        )
    }
}

@Composable
private fun TabletLayout(uiState: MainUiState, viewModel: MainViewModel, navToRoutineDetail: (Int) -> Unit) {
    val fondo = Color(0xFF1C2120)
    val currentScore = remember {
        mutableIntStateOf(7)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize(1f)
            .background(fondo),
    ) {
        LaunchedEffect(key1 = true) {
            viewModel.getRoutines() // Llama a getRoutines solo una vez cuando se carga la pantalla
        }
        Text(text = "Rutina recomendada: ", color = Color.White, fontSize = 30.sp)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            content = {
                //viewModel.getRoutines()
                val reviews = viewModel.uiState.reviewList
                val routines = viewModel.uiState.routines
                val favorites = viewModel.uiState.favorites
                var scoreProm = 0.0;
                if(!routines.isNullOrEmpty()) {
                    itemsIndexed(routines) { index, routine ->
                        LaunchedEffect(key1 = true) {
                            viewModel.getReviews(routine.id)
                        }
                    }
                }
                if (reviews.isNotEmpty()) {
                    val hasReviewAboveCurrentScore = reviews.any { it.score > currentScore.intValue }
                    var flag = false
                    if (hasReviewAboveCurrentScore) {
                        itemsIndexed(reviews) { index, review ->
                            if (review.score > currentScore.intValue) {
                                routines!!.forEach{ routine->
                                    val isFaved = favorites?.any { it.id == routine.id } ?: false
                                    if(routine.id == review.routineId && flag == false){
                                        flag = true
                                        RoutineCard(viewModel,routine = routine, func = navToRoutineDetail , isFaved = isFaved, modifier = Modifier.clickable(onClick = { navToRoutineDetail(routine.id) }), isPhone = true)
                                    }
                                }
                                if(review.review != "") {
                                    Box(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .background(
                                                color = Color.Gray,
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                            .padding(12.dp)
                                    ) {
                                        Column {
                                            Text(
                                                text = "Puntuacion valorada por el usuario: " + review.score.toString(),
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text(
                                                text = review.review,
                                                color = Color.White,
                                                fontSize = 14.sp
                                            )
                                        }
                                    }
                                }
                            }
                            if (index == reviews.size - 1) {
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
                            // Mostrar mensaje cuando ninguna revisión tenga una puntuación mayor a currentScore.intValue
                            NoReviewsScreen()
                        }
                    }
                } else {
                    item {
                        // Mostrar mensaje cuando no haya revisiones en absoluto
                        NoReviewsScreen()
                    }
                }
            }
        )
    }
}


@Composable
fun NoReviewsScreen() {
    val gymImages = listOf(
        R.drawable.gym,
        R.drawable.ejercicio_1,
        R.drawable.login_img,
        R.drawable.register_img,
        R.drawable.gym,
        R.drawable.ejercicio_1
    )

    val inspirationalMessages = listOf(
        "¡Entrena con pasión!",
        "El éxito no llega sin esfuerzo.",
        "Tu cuerpo es tu templo."
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .background(color = Color.Red)
    ) {
        Text(
            text = "¡HAZ EJERCICIO CON STROGIFY!",
            fontSize = 24.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp) // Añadir padding al texto para separarlo del borde del Box
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp) // Espacio entre las imágenes
    ) {
        for (i in gymImages.indices step 2) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp) // Espacio entre las imágenes en cada columna
            ) {
                Image(
                    painter = painterResource(id = gymImages.getOrElse(i) { R.drawable.register_img }), // Reemplaza "R.drawable.placeholder" con tu lógica para obtener la imagen en el índice "i"
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(shape = RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                // Verifica si hay una segunda imagen en el par
                if (i + 1 < gymImages.size) {
                    Image(
                        painter = painterResource(id = gymImages.getOrElse(i + 1) { R.drawable.register_img }), // Reemplaza "R.drawable.placeholder" con tu lógica para obtener la imagen en el índice "i + 1"
                        contentDescription = null,
                        modifier = Modifier
                            .size(150.dp)
                            .padding(start = 8.dp)
                            .clip(shape = RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }

    var currentIndex = remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        val ticker = ticker(delayMillis = 5000)
        try {
            for (event in ticker) {
                currentIndex.intValue = (currentIndex.intValue + 1) % inspirationalMessages.size
            }
        } finally {
            ticker.cancel()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally ,
        verticalArrangement = Arrangement.Bottom
    ) {
        // Encabezado

        // Imágenes de gimnasio (código de imágenes)
        // Texto dinámico que cambia cada 5 segundos
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(color = Color.LightGray, shape = RoundedCornerShape(20.dp))
        ) {
            Text(
                text = inspirationalMessages[currentIndex.intValue],
                fontSize = 24.sp,
                color = Color.Black,
                fontFamily = FontFamily.Monospace, // Cambio de fuente a una cursive para un estilo más decorativo
                //fontStyle = FontStyle.Italic, // Texto en cursiva
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }

        // Texto relacionado con ejercicio
    }
}



