package com.example.strongify

import android.app.Application
import com.example.strongify.data.network.CyclesExercisesRemoteDataSource
import com.example.strongify.data.network.FavouriteRemoteDataSource
import com.example.strongify.data.network.RoutineRemoteDataSource
import com.example.strongify.data.network.RoutinesCyclesRemoteDataSource
import com.example.strongify.data.network.SportRemoteDataSource
import com.example.strongify.data.network.UserRemoteDataSource
import com.example.strongify.data.network.api.RetrofitClient
import com.example.strongify.data.repository.CyclesExercisesRepository
import com.example.strongify.data.repository.FavouriteRepository
import com.example.strongify.data.repository.RoutineRepository
import com.example.strongify.data.repository.RoutinesCyclesRepository
import com.example.strongify.data.repository.SportRepository
import com.example.strongify.data.repository.UserRepository
import com.example.strongify.util.SessionManager

class Strongify : Application() {

    private val userRemoteDataSource: UserRemoteDataSource
        get() = UserRemoteDataSource(sessionManager, RetrofitClient.getApiUserService(this))

    private val sportRemoteDataSource: SportRemoteDataSource
        get() = SportRemoteDataSource(RetrofitClient.getApiSportService(this))

    private val routineRemoteDataSource: RoutineRemoteDataSource
        get() = RoutineRemoteDataSource(RetrofitClient.getApiRoutineService(this))

    private val routinesCyclesRemoteDataSource: RoutinesCyclesRemoteDataSource
        get() = RoutinesCyclesRemoteDataSource(RetrofitClient.getApiRoutineCycle(this))

    private val cyclesExercisesRemoteDataSource: CyclesExercisesRemoteDataSource
        get() = CyclesExercisesRemoteDataSource(RetrofitClient.getApiCycleExercise(this))

    private val favouriteRemoteDataSource: FavouriteRemoteDataSource
        get() = FavouriteRemoteDataSource(RetrofitClient.getApiFavouriteService(this))

    val sessionManager: SessionManager
        get() = SessionManager(this)

    val userRepository: UserRepository
        get() = UserRepository(userRemoteDataSource)

    val sportRepository: SportRepository
        get() = SportRepository(sportRemoteDataSource)

    val routineRepository: RoutineRepository
        get() = RoutineRepository(routineRemoteDataSource)

    val routineCycleRepository: RoutinesCyclesRepository
        get() = RoutinesCyclesRepository(routinesCyclesRemoteDataSource)

    val cyclesExercisesRepository: CyclesExercisesRepository
        get() = CyclesExercisesRepository(cyclesExercisesRemoteDataSource)

    val favouriteRepository: FavouriteRepository
        get() = FavouriteRepository(favouriteRemoteDataSource)
}