package com.example.strongify.data.network.api

import com.example.strongify.data.network.model.NetworkCompleteCycleExercise
import com.example.strongify.data.network.model.NetworkPagedContent
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiCyclesExercisesService {
    @GET("cycles/{cycleId}/exercises")
    suspend fun getCycleExercises(@Path("cycleId") cycleId: Int) : Response<NetworkPagedContent<NetworkCompleteCycleExercise>>
}