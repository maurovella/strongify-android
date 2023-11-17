package com.example.strongify.data.network

import com.example.strongify.data.network.api.ApiExerciseService
import com.example.strongify.data.network.model.NetworkExercise
import com.example.strongify.data.network.model.NetworkPagedContent


class ExerciseRemoteDataSource(
    private val apiExerciseService: ApiExerciseService
): RemoteDataSource() {

    suspend fun getExercises(page: Int): NetworkPagedContent<NetworkExercise> {
        return handleApiResponse {
            apiExerciseService.getExercises(page)
        }
    }
    suspend fun getExercise(exerciseId: Int) : NetworkExercise {
        return handleApiResponse {
            apiExerciseService.getExercise(exerciseId)
        }
    }
}