package com.example.strongify.data.network.api

import com.example.strongify.data.network.model.NetworkCompleteCycle
import com.example.strongify.data.network.model.NetworkPagedContent
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRoutinesCyclesService {
    @GET("routines/{routineId}/cycles")
    suspend fun getRoutinesCycles(@Path("routineId") routineId: Int, @Query("page") page: Int) : Response<NetworkPagedContent<NetworkCompleteCycle>>
}