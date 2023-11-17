package com.example.strongify.data.network

import com.example.strongify.data.network.api.ApiRoutineService
import com.example.strongify.data.network.model.NetworkPagedContent
import com.example.strongify.data.network.model.NetworkRoutine


class RoutineRemoteDataSource (
    private val apiRoutineService : ApiRoutineService
) : RemoteDataSource() {

    suspend fun getRoutines(page: Int) : NetworkPagedContent<NetworkRoutine> {
        return handleApiResponse {
            apiRoutineService.getRoutines(page)
        }
    }

    suspend fun getRoutinesWFilter(page: Int, order: String, dir: String) : NetworkPagedContent<NetworkRoutine>{
        return handleApiResponse {
            apiRoutineService.getRoutinesWFilter(page, order,dir)
        }
    }


    suspend fun getRoutine(routineId: Int) : NetworkRoutine {
        return handleApiResponse {
            apiRoutineService.getRoutine(routineId)
        }
    }

}