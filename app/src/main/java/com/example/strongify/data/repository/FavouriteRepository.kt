package com.example.strongify.data.repository

import com.example.strongify.data.model.Routine
import com.example.strongify.data.network.FavouriteRemoteDataSource
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class FavouriteRepository (
    private val remoteDataSource: FavouriteRemoteDataSource
){
    private val favouriteMutex = Mutex()
    private var favouriteRoutines: List<Routine> = emptyList()

    suspend fun getFavourites(): List<Routine>{
        var page = 0
        this.favouriteRoutines = emptyList()

        do {
            val result = remoteDataSource.getFavourites(page)
            favouriteMutex.withLock {
                this.favouriteRoutines = this.favouriteRoutines.plus(result.content.map { it.asModel() })
            }
            page++
        } while(!result.isLastPage)
        return favouriteMutex.withLock { this.favouriteRoutines }
    }

    suspend fun markFavourite(routineId: Int){
        remoteDataSource.markFacourite(routineId)
        favouriteMutex.withLock {
            this.favouriteRoutines = emptyList()
        }
    }

    suspend fun removeFavourite(routineId: Int){
        remoteDataSource.deleteFavourite(routineId)
        favouriteMutex.withLock {
            this.favouriteRoutines = emptyList()
        }
    }
}