package com.example.strongify.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.strongify.R

@Composable
fun ExerciseCard(
    name: String,
    repetitions: Int,
    series: Int,
    currentSerie: Int,
    difficulty: String,
    image: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ejercicio_1),
                contentDescription = name + "_image",
                modifier = Modifier
                    .width(200.dp)
            )
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = name
                //    fontUnit = 20.dp
                )
                Text(text = "Repetitions: $repetitions")
                Text(text = "Series: $series")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "Current Series: $currentSerie")
                    Button(
                        onClick = { /*TODO*/}
                    ) {
                        Text(text = "Finish series")
                    }
                }

            }
        }
    }
}