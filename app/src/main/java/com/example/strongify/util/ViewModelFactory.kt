package com.example.strongify.util

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.strongify.data.repository.SportRepository
import com.example.strongify.data.repository.UserRepository
import com.example.strongify.MainViewModel
import com.example.strongify.data.repository.CyclesExercisesRepository
import com.example.strongify.data.repository.FavouriteRepository
import com.example.strongify.data.repository.RoutineRepository
import com.example.strongify.data.repository.RoutinesCyclesRepository

class ViewModelFactory constructor(
    private val sessionManager: SessionManager,
    private val userRepository: UserRepository,
    private val sportRepository: SportRepository,
    private val routineRepository: RoutineRepository,
    private val favouriteRepository: FavouriteRepository,
    private val routinesCyclesRepository: RoutinesCyclesRepository,
    private val cyclesExercisesRepository: CyclesExercisesRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = with(modelClass) {
        when {
            isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel(sessionManager, userRepository, sportRepository, routineRepository, routinesCyclesRepository, cyclesExercisesRepository, favouriteRepository)

            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}