package com.example.strongify.ui.components
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.strongify.R

@Composable
fun RoutineCard(
    category: String,
    routineName: String,
    difficulty: String // Agregamos el parámetro de dificultad
) {
    val difficultyColor = when (difficulty) {
        "Novato", "Principiante" -> Color.Green
        "Intermedio" -> Color.Yellow
        "Avanzado" -> Color.Red
        "Experto" -> Color.Black
        else -> Color.Transparent
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Nombre de la rutina y botón de inicio
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(
                    text = routineName,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.clickable { /* Acción al hacer clic en el icono de información */ }
                    )
                    Text(
                        text = difficulty,
                        color = Color.White,
                        modifier = Modifier
                            .background(difficultyColor)
                            .padding(4.dp)
                    )
                }
            }

            // Icono de corazón
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.padding(8.dp).size(32.dp).clickable { /* Acción al hacer clic en el icono de corazón */ }
            )
        }

        // Imagen por defecto (¡Recuerda reemplazar el recurso con la imagen adecuada!)
        Image(
            painter = painterResource(R.drawable.gym),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.5f) // Proporción más ancha que larga
                .clip(shape = RoundedCornerShape(8.dp))
        )
    }
}