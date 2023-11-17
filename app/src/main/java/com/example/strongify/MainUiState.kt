package com.example.strongify

import com.example.strongify.data.model.CompleteCycle
import com.example.strongify.data.model.CompleteCycleExercise
import com.example.strongify.data.model.CycleData
import com.example.strongify.data.model.Error
import com.example.strongify.data.model.Routine
import com.example.strongify.data.model.Sport
import com.example.strongify.data.model.User

data class MainUiState(
    val isAuthenticated: Boolean = false,
    val isFetching: Boolean = false,
    val currentUser: User? = null,
    val sports: List<Sport>? = null,
    val currentSport: Sport? = null,
    val error: Error? = null,
    val routines: List<Routine>? = null,
    val favorites: List<Routine>? = null,
    val order: String? = "date",
    val dir: String? = "asc",
    val routinesCycles: List<CompleteCycle>? = null,
    val cycleExercise: List<CompleteCycleExercise>? = null,
    var cycleDataList: List<CycleData> = emptyList(),
)

val MainUiState.canGetCurrentUser: Boolean get() = isAuthenticated
val MainUiState.canGetAllSports: Boolean get() = isAuthenticated
val MainUiState.canGetCurrentSport: Boolean get() = isAuthenticated && currentSport != null
val MainUiState.canAddSport: Boolean get() = isAuthenticated && currentSport == null
val MainUiState.canModifySport: Boolean get() = isAuthenticated && currentSport != null
val MainUiState.canDeleteSport: Boolean get() = canModifySport
