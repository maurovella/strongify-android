package com.example.strongify.data.network

import com.example.strongify.data.network.api.ApiRoutinesCyclesService
import com.example.strongify.data.network.model.NetworkCompleteCycle
import com.example.strongify.data.network.model.NetworkPagedContent


class RoutinesCyclesRemoteDataSource (
    private val routinesCyclesService: ApiRoutinesCyclesService
) : RemoteDataSource() {

    suspend fun getRoutinesCycles(routineId: Int, page: Int) : NetworkPagedContent<NetworkCompleteCycle> {
        return handleApiResponse {
            routinesCyclesService.getRoutinesCycles(routineId, page)
        }
    }
}