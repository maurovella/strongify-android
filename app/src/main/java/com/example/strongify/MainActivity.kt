package com.example.strongify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.strongify.data.model.Sport
import com.example.strongify.ui.theme.ApiTheme
import com.example.strongify.util.getViewModelFactory
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun ActionButton(
    @StringRes resId: Int,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        enabled = enabled,
        onClick = onClick,
    ) {
        Text(
            text = stringResource(resId),
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory())
) {
    val uiState = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        if (!uiState.isAuthenticated) {
            ActionButton(
                resId = R.string.login,
                onClick = {
                    viewModel.login("johndoee", "1234567890")
                })
        } else {
            ActionButton(
                resId = R.string.logout,
                onClick = {
                    viewModel.logout()
                })
        }

        ActionButton(
            resId = R.string.get_current_user,
            enabled = uiState.canGetCurrentUser,
            onClick = {
                viewModel.getCurrentUser()
            })
        ActionButton(
            resId = R.string.get_all_sports,
            enabled = uiState.canGetAllSports,
            onClick = {
                viewModel.getSports()
            })
        ActionButton(
            resId = R.string.get_current_sport,
            enabled = uiState.canGetCurrentSport,
            onClick = {
                val currentSport = uiState.currentSport!!
                viewModel.getSport(currentSport.id!!)
            })
        ActionButton(
            resId = R.string.add_sport,
            enabled = uiState.canAddSport,
            onClick = {
                val random = Random.nextInt(0, 100)
                val sport = Sport(name = "Sport $random", detail = "Detail $random")
                viewModel.addOrModifySport(sport)
            })
        ActionButton(
            resId = R.string.modify_sport,
            enabled = uiState.canModifySport,
            onClick = {
                val random = Random.nextInt(0, 100)
                val currentSport = uiState.currentSport!!
                val sport = Sport(currentSport.id, currentSport.name, detail = "Detail $random")
                viewModel.addOrModifySport(sport)
            })
        ActionButton(
            resId = R.string.delete_sport,
            enabled = uiState.canDeleteSport,
            onClick = {
                val currentSport = uiState.currentSport!!
                viewModel.deleteSport(currentSport.id!!)
            })
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            val currentUserData = uiState.currentUser?.let {
                "Current User: ${it.firstName} ${it.lastName} (${it.email})"
            }
            Text(
                text = currentUserData ?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                fontSize = 18.sp
            )
            val currentSportData = uiState.currentSport?.let {
                "Current Sport: (${it.id}) ${it.name} - ${it.detail}"
            }
            Text(
                text = currentSportData ?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                fontSize = 18.sp
            )
            Text(
                text = "Total Sports: ${uiState.sports?.size ?: "unknown"}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                fontSize = 18.sp
            )
            if (uiState.error != null) {
                Text(
                    text = "${uiState.error.code} - ${uiState.error.message}",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    fontSize = 18.sp
                )
            }
        }
    }
}