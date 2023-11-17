package com.example.strongify.data.network

import com.example.strongify.data.network.api.ApiFavouriteService
import com.example.strongify.data.network.model.NetworkPagedContent
import com.example.strongify.data.network.model.NetworkRoutine


class FavouriteRemoteDataSource (
    private val apiFavouriteService : ApiFavouriteService
) : RemoteDataSource(){

    suspend fun getFavourites(page: Int) : NetworkPagedContent<NetworkRoutine> {
        return handleApiResponse {
            apiFavouriteService.getFavourites(page)
        }
    }

    suspend fun markFacourite(routineId: Int){
        return handleApiResponse {
            apiFavouriteService.markFavourite(routineId)
        }
    }

    suspend fun deleteFavourite(routineId: Int){
        return handleApiResponse {
            apiFavouriteService.removeFavourtie(routineId)
        }
    }
}