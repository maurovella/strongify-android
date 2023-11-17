package com.example.strongify.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
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
fun RegisterScreen() {
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
                    painter = painterResource(id = R.drawable.register_img),
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
                        text = "Register", // Update text for registration
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
                            //textColor = Color.White
                        ),
                    )

                    LabeledTextField(
                        label = "Email", // Title above the text field
                        value = "", // Empty string for user to input email
                        onValueChange = { /* Handle the value change */ },
                        visualTransformation = VisualTransformation.None,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .fillMaxWidth(0.8f), // Set to occupy 80% of the width
                        shape = MaterialTheme.shapes.medium,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color(0xFF8F8E8E),
                            //textColor = Color.White
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
                            //textColor = Color.White
                        ),
                    )

                    // Similarly add more LabeledTextFields for additional fields

                    Button(
                        onClick = { /* Handle registration button click */ },
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
                            text = "Register", // Change button text
                            fontSize = 16.sp,
                            modifier = Modifier.padding(0.dp)
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabeledTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors()
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 15.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            visualTransformation = visualTransformation,
            label = { Text(text = label, fontSize = 15.sp, color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            shape = shape,
            colors = colors
        )
    }
}

