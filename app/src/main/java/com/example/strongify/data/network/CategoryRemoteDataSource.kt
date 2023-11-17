package com.example.strongify.data.network

import com.example.strongify.data.network.api.ApiCategoryService
import com.example.strongify.data.network.model.NetworkCategory
import com.example.strongify.data.network.model.NetworkPagedContent

class CategoryRemoteDataSource(
    private val apiCategoryService : ApiCategoryService
) : RemoteDataSource() {

    suspend fun getCategories(): NetworkPagedContent<NetworkCategory> {
        return handleApiResponse {
            apiCategoryService.getCategories()
        }
    }
    suspend fun getCategory(categoryId: Int): NetworkCategory {
        return handleApiResponse {
            apiCategoryService.getCategory(categoryId)
        }
    }

    suspend fun addCategory(category: NetworkCategory) : NetworkCategory {
        return handleApiResponse {
            apiCategoryService.addCategory(category)
        }
    }

}