package com.example.strongify.data.network


import com.example.strongify.data.network.api.ApiCyclesExercisesService
import com.example.strongify.data.network.model.NetworkCompleteCycleExercise
import com.example.strongify.data.network.model.NetworkPagedContent

class CyclesExercisesRemoteDataSource(
    private val cyclesExercisesService: ApiCyclesExercisesService
) : RemoteDataSource() {

    suspend fun getCycleExercises(cycleId: Int) : NetworkPagedContent<NetworkCompleteCycleExercise> {
        return handleApiResponse {
            cyclesExercisesService.getCycleExercises(cycleId)
        }
    }
}