package com.example.strongify.data.network.api

import com.example.strongify.data.network.model.NetworkExercise
import com.example.strongify.data.network.model.NetworkPagedContent
import com.example.strongify.data.network.model.NetworkSport
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiExerciseService {
    @GET("exercises")
    suspend fun getExercises(@Query("page") page: Int) : Response<NetworkPagedContent<NetworkExercise>>

    @GET("exercises/{exerciseId}")
    suspend fun getExercise(@Path("exerciseId") exerciseId: Int) : Response<NetworkExercise>
}