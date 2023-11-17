package com.example.strongify

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strongify.data.DataSourceException
import com.example.strongify.data.model.CycleData
import com.example.strongify.data.model.Sport
import com.example.strongify.data.repository.SportRepository
import com.example.strongify.data.repository.UserRepository
import com.example.strongify.util.SessionManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.example.strongify.data.model.Error
import com.example.strongify.data.model.Review
import com.example.strongify.data.repository.CyclesExercisesRepository
import com.example.strongify.data.repository.FavouriteRepository
import com.example.strongify.data.repository.ReviewRepository
import com.example.strongify.data.repository.RoutineRepository
import com.example.strongify.data.repository.RoutinesCyclesRepository

class MainViewModel(
    sessionManager: SessionManager,
    private val userRepository: UserRepository,
    private val sportRepository: SportRepository,
    private val routineRepository: RoutineRepository,
    private val routinesCyclesRepository: RoutinesCyclesRepository,
    private val cyclesExercisesRepository: CyclesExercisesRepository,
    private val favouriteRepository: FavouriteRepository,
    private val reviewRepository: ReviewRepository
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

    fun getRoutines() {
        runOnViewModelScope(
            { routineRepository.getRoutines() },
            { state, response -> state.copy(routines = response) }
        )
        getFavorites()
    }

    fun getFilteredRoutines(order: String = "date", dir: String = "asc") {
        runOnViewModelScope(
            { routineRepository.getRoutinesWFilter(order, dir) },
            { state, response -> state.copy(routines = response) }
        )
        getFavorites()
    }

    fun getRoutineCycles(routineId: Int) = runOnViewModelScope(
        { routinesCyclesRepository.getRoutineCycles(routineId, true) },
        { state, response -> state.copy( routinesCycles = response) }
    )

    fun getCycleExercises(cycleId: Int) = runOnViewModelScope(
        { cyclesExercisesRepository.getCycleExercises(cycleId, true) },
        { state, response -> state.copy( cycleExercise = response) }
    )

    fun getRoutineDetail(routineId: Int) = runOnViewModelScope(
        { getRoutineCycles(routineId).join()
            for(cycle in uiState.routinesCycles) {
                getCycleExercises(cycle.id).join()

                uiState.cycleDataList = uiState.cycleDataList.plus(CycleData(cycle.name, cycle.repetitions, uiState.cycleExercise!!))
            }
        },
        { state, response -> state.copy() }
    )

    fun getFavorites() {
        runOnViewModelScope(
            { favouriteRepository.getFavourites() },
            { state, response -> state.copy(favorites = response) }
        )
    }

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

    fun addFavorite(routineId: Int) {
        runOnViewModelScope(
            { favouriteRepository.markFavourite(routineId = routineId) },
            { state, response ->
                state.copy()
            }
        )
        getFavorites()
    }

    fun deleteFavorite(routineId: Int) {
        runOnViewModelScope(
            { favouriteRepository.removeFavourite(routineId) },
            { state, response ->
                state.copy()
            }
        )
        getFavorites()
    }

    suspend fun getRoutine(routineId: Int) {
        runOnViewModelScope(
            { routineRepository.getRoutine(routineId) },
            { state, response ->
                state.copy(
                    currentRoutine = response
                )
            }
        )
        getRoutineDetail(routineId).join()
    }

    fun setRate(routineId: Int, score: Int, reviewText: String) {
        runOnViewModelScope(
            { reviewRepository.addReview(routineId, Review(score,routineId,reviewText)) },
            { state, response -> state.copy() }
        )
    }

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