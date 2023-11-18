package com.example.strongify.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.strongify.MainUiState
import com.example.strongify.MainViewModel
import com.example.strongify.R
import com.example.strongify.util.getViewModelFactory


@Composable
fun HomeScreen(
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
    isPhone: Boolean = true
) {
    val uiState = viewModel.uiState
    Surface {
        if (isPhone) {
            PhoneLayout(uiState = uiState, viewModel = viewModel)
        } else {
            TabletLayout(uiState = uiState, viewModel = viewModel)
        }
    }
}

@Composable
fun PhoneLayout(uiState: MainUiState, viewModel: MainViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(1f)
    ) {
        Text(
            text = stringResource(R.string.nav_home),
            fontSize = 30.sp
        )
        Button(
            onClick = {
                viewModel.getCurrentUser()
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Button for Phone")
        }
        val currentUserData = uiState.currentUser?.let {
            "Current User: ${it.firstName} ${it.lastName} (${it.email})"
        }
        Text(
            text = currentUserData ?: "NO HAY USUARIO",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            fontSize = 18.sp
        )
    }
}

@Composable
fun TabletLayout(uiState: MainUiState, viewModel: MainViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(1f)
    ) {
        Text(
            text = stringResource(R.string.nav_home),
            fontSize = 40.sp
        )
        ElevatedButton(
            onClick = {
                viewModel.getCurrentUser()
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Button for Tablet")
        }
        val currentUserData = uiState.currentUser?.let {
            "Current User: ${it.firstName} ${it.lastName} (${it.email})"
        }
        Text(
            text = currentUserData ?: "NO HAY USUARIO",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            fontSize = 24.sp
        )
    }
}
