package com.example.strongify.ui.screens

import android.annotation.SuppressLint
import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.strongify.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun LoginScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF1C2120))
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Image(
                    painter = painterResource(id = R.drawable.login_img),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(shape = RoundedCornerShape(16.dp))
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Login", // Update text for login
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )

                    LabeledTextField(
                        label = "Username", // Title above the text field
                        value = "", // Empty string for user to input username
                        onValueChange = { /* Handle the value change */ },
                        visualTransformation = VisualTransformation.None,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .fillMaxWidth(0.8f), // Set to occupy 80% of the width
                        shape = MaterialTheme.shapes.medium,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color(0xFF8F8E8E),
                            textColor = Color.White
                        ),
                    )

                    LabeledTextField(
                        label = "Password", // Title above the text field
                        value = "", // Empty string for user to input password
                        onValueChange = { /* Handle the value change */ },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .fillMaxWidth(0.8f), // Set to occupy 80% of the width
                        shape = MaterialTheme.shapes.medium,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color(0xFF8F8E8E),
                            textColor = Color.White
                        ),
                    )

                    Button(
                        onClick = { /* Handle login button click */ },
                        modifier = Modifier
                            .fillMaxWidth(0.8f) // Set to occupy 80% of the width
                            .height(60.dp),
                        shape = MaterialTheme.shapes.medium, // Optional: Apply a shape to make it rectangular
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF1414),
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            top = 0.dp,
                            end = 16.dp,
                            bottom = 0.dp,
                        ),
                        enabled = true
                    ) {
                        Text(
                            text = "Login", // Change button text
                            fontSize = 16.sp,
                            modifier = Modifier.padding(0.dp)
                        )
                    }
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp)
//                            .clickable {
//                                // Handle click action for registration link
//                                scope.launch {
//                                    // Navigate to the RegisterScreen when the text is clicked
//                                    uriHandler.openUri("yourapp://register") // Replace with your custom URI
//                                }
//                            }
//                    ) {
//                        val message = "¿No tienes una cuenta? ¡Regístrate!"
//
//                        Text(
//                            text = AnnotatedString(message),
//                            color = LocalContentColor.current,
//                            modifier = Modifier.fillMaxWidth(),
//                            style = MaterialTheme.typography.bodySmall,
//                            maxLines = 1
//                        ).withStyle(style = SpanStyle(textDecoration = android.text.style.TextAppearance().apply {
//                            textDecoration = Paint.UNDERLINE_TEXT_FLAG
//                        })) {
//                            // Style only the "Regístrate" text as a link
//                            addStyle(
//                                style = SpanStyle(
//                                    color = MaterialTheme.colors.secondary,
//                                    textDecoration = android.text.style.TextAppearance().apply {
//                                        textDecoration = Paint.UNDERLINE_TEXT_FLAG
//                                    }
//                                ),
//                                start = message.indexOf("Regístrate"),
//                                end = message.length
//                            )
//                        }
//                    }
                }
            }
        }
    }
}


