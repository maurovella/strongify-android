package com.example.strongify

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strongify.data.DataSourceException
import com.example.strongify.data.model.Sport
import com.example.strongify.data.repository.SportRepository
import com.example.strongify.data.repository.UserRepository
import com.example.strongify.util.SessionManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.example.strongify.data.model.Error

class MainViewModel(
    sessionManager: SessionManager,
    private val userRepository: UserRepository,
    private val sportRepository: SportRepository
) : ViewModel() {

    var uiState by mutableStateOf(MainUiState(isAuthenticated = sessionManager.loadAuthToken() != null))
        private set

    fun login(username: String, password: String, successFunc: () -> Unit, failedFunc: suspend (String) -> Unit) = runOnViewModelScopeLogin(
        { userRepository.login(username, password) },
        { state, _ -> state.copy(isAuthenticated = true) },
        successFunc,
        failedFunc
    )

    fun logout() = runOnViewModelScope(
        { userRepository.logout() },
        { state, _ ->
            state.copy(
                isAuthenticated = false,
                currentUser = null,
                currentSport = null,
                sports = null
            )
        }
    )

    fun getCurrentUser() = runOnViewModelScope(
        { userRepository.getCurrentUser(uiState.currentUser == null) },
        { state, response -> state.copy(currentUser = response) }
    )

    fun getSports() = runOnViewModelScope(
        { sportRepository.getSports(true) },
        { state, response -> state.copy(sports = response) }
    )

    fun getSport(sportId: Int) = runOnViewModelScope(
        { sportRepository.getSport(sportId) },
        { state, response -> state.copy(currentSport = response) }
    )

    fun addOrModifySport(sport: Sport) = runOnViewModelScope(
        {
            if (sport.id == null)
                sportRepository.addSport(sport)
            else
                sportRepository.modifySport(sport)
        },
        { state, response ->
            state.copy(
                currentSport = response,
                sports = null
            )
        }
    )

    fun deleteSport(sportId: Int) = runOnViewModelScope(
        { sportRepository.deleteSport(sportId) },
        { state, response ->
            state.copy(
                currentSport = null,
                sports = null
            )
        }
    )

    private fun <R> runOnViewModelScopeLogin(
        block: suspend () -> R,
        updateState: (MainUiState, R) -> MainUiState,
        succesFunc: () -> Unit,
        failedFunc: suspend (String) -> Unit
    ): Job = viewModelScope.launch {
        uiState = uiState.copy(isFetching = true, error = null)
        runCatching {
            block()
        }.onSuccess { response ->
            uiState = updateState(uiState, response).copy(isFetching = false)
            succesFunc()
        }.onFailure { e ->
            uiState = uiState.copy(isFetching = false, error = handleError(e))
            failedFunc(e.message!!)
        }
    }

    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (MainUiState, R) -> MainUiState,
    ): Job = viewModelScope.launch {
        uiState = uiState.copy(isFetching = true, error = null)
        runCatching {
            block()
        }.onSuccess { response ->
            uiState = updateState(uiState, response).copy(isFetching = false)
        }.onFailure { e ->
            uiState = uiState.copy(isFetching = false, error = handleError(e))
        }
    }

    private fun handleError(e: Throwable): Error {
        return if (e is DataSourceException) {
            Error(e.code, e.message ?: "", e.details)
        } else {
            Error(null, e.message ?: "", null)
        }
    }
}