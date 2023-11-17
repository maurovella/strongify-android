package com.example.strongify.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.strongify.MainViewModel
import com.example.strongify.R
import com.example.strongify.util.getViewModelFactory

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun LoginScreen(
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
    onLogin: () -> Unit = {},
    navToRegister: () -> Unit = {}
) {
    val userState = remember { mutableStateOf("") }
    val pswState = remember { mutableStateOf("") }
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
                        text = stringResource(id = R.string.login), // Update text for login
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    LabeledTextField(
                        label = stringResource(id = R.string.username), // Title above the text field
                        value = userState.value,
                        onValueChange = { userState.value = it },
                        visualTransformation = VisualTransformation.None,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .fillMaxWidth(0.8f), // Set to occupy 80% of the width
                        shape = MaterialTheme.shapes.medium,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color(0xFF8F8E8E),
                            //color = Color.White
                        ),
                    )

                    LabeledTextField(
                        label = stringResource(id = R.string.password), // Title above the text field
                        value = pswState.value, // Empty string for user to input password
                        onValueChange = { pswState.value = it },
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

                    Button(
                        onClick = {
                            viewModel.login(userState.value,pswState.value,
                                {
                                    onLogin()
                                },
                                {
                                    if (it == "Connection error")
                                        Toast.makeText(context,"Error!",Toast.LENGTH_LONG).show()

                                    if (it == "Invalid username or password" || it == "") {
                                        val errorMessage = context.getString(R.string.login_error)
                                        Toast.makeText(context, errorMessage,Toast.LENGTH_LONG).show()
                                    }
                                }
                            )
                        },
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
                            text = stringResource(id = R.string.login), // Change button text
                            fontSize = 16.sp,
                            modifier = Modifier.padding(0.dp)
                        )
                    }

                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(id = R.string.no_account_msg))
                            withStyle(style = SpanStyle(color = Color.Red)) {
                                append(stringResource(id = R.string.register_cta))
                            }
                        },
                        fontSize = 14.sp,
                        modifier = Modifier
                            .clickable {
                                navToRegister()
                            }
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp)
                    )
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


