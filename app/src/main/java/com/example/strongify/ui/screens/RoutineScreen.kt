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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.strongify.R
import com.example.strongify.ui.components.RoutineCard

@Composable
fun RoutineScreen(
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
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                content = {
                    itemsIndexed(routines) { _, routine ->
                        RoutineCard(
                            category = routine.category,
                            routineName = routine.routineName,
                            difficulty = routine.difficulty,
                            modifier = Modifier.clickable(onClick = { navToRoutineDetail(routine.routineId) })
                        )
                    }
                }
            )
        }
    }
}


data class Routine(
    val category: String,
    val routineName: String,
    val routineId: Int,
    val difficulty: String
)

val routines = listOf(
    Routine("Cardio", "Cardio Workout", 1, "Novato"),
    Routine("Fuerza", "Rutina de Fuerza", 2, "Intermedio"),
    Routine("Flexibilidad", "Estiramiento", 3, "Principiante"),
    Routine("Yoga", "Clase de Yoga", 4, "Avanzado")
)