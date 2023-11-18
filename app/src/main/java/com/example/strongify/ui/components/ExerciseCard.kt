package com.example.strongify.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.strongify.R

@Composable
fun ExerciseCard(
    name: String,
    repetitions: Int,
    series: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ejercicio_1),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = name,
                    color = Color.LightGray,
                    textAlign = TextAlign.Start,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif, // Cambia a la fuente que desees
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Repetitions: $repetitions",
                    color = Color.LightGray,textAlign = TextAlign.Start,
                    fontFamily = FontFamily.SansSerif, // Cambia a la fuente que desees
                    fontWeight = FontWeight.Bold
                    )
                Text(
                    text = "Series: $series",
                    color = Color.LightGray,textAlign = TextAlign.Start,
                    fontFamily = FontFamily.SansSerif, // Cambia a la fuente que desees
                    fontWeight = FontWeight.Bold
                )

            }
        }
    }
}