package com.example.strongify.util

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import com.example.strongify.Strongify
import com.example.strongify.data.repository.CyclesExercisesRepository
import com.example.strongify.data.repository.RoutinesCyclesRepository

@Composable
fun getViewModelFactory(defaultArgs: Bundle? = null): ViewModelFactory {
    val application = (LocalContext.current.applicationContext as Strongify)
    val sessionManager = application.sessionManager
    val userRepository = application.userRepository
    val sportRepository = application.sportRepository
    val routineRepository = application.routineRepository
    val routinesCyclesRepository = application.routineCycleRepository
    val cyclesExercisesRepository = application.cyclesExercisesRepository
    val favouriteRepository = application.favouriteRepository
    val reviewRepository = application.reviewRepository
    return ViewModelFactory(
        sessionManager,
        userRepository,
        sportRepository,
        routineRepository,
        favouriteRepository,
        routinesCyclesRepository,
        cyclesExercisesRepository,
        reviewRepository,
        LocalSavedStateRegistryOwner.current,
        defaultArgs
    )
}