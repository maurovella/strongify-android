package com.example.strongify.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.strongify.MainViewModel
import com.example.strongify.R

@Composable
fun RateDialog(
    modifier: Modifier,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    viewModel: MainViewModel,
    routineId: Int,
    isPhone: Boolean
) {
    var score by remember { mutableStateOf("") }
    var reviewText by remember { mutableStateOf("") }

    // Esta función convierte el texto del puntaje en un número
    fun isValidScore(text: String): Boolean {
        return text.toIntOrNull() in 1..10
    }

    if (isPhone) {
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
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState()),
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
                                onClick = onCancel
                            ) {
                                Text(text = "Cancelar")
                            }
                            Button(
                                onClick = {
                                    onConfirm()
                                    if (score != "")
                                        viewModel.setRate(routineId, score.toInt(), reviewText)
                                },
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Text(text = "Confirmar")
                            }

                        }
                    }
                }
            }
        )
    } else {
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
                                onClick = onCancel
                            ) {
                                Text(text = "Cancelar")
                            }
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

                        }
                    }
                }
            }
        )
    }
}