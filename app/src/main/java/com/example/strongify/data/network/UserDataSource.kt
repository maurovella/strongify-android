package com.example.strongify.data.network

import com.example.strongify.data.network.api.ApiUserService
import com.example.strongify.data.network.model.NetworkCredentials
import com.example.strongify.data.network.model.NetworkUser
import com.example.strongify.util.SessionManager

class UserRemoteDataSource(
    private val sessionManager: SessionManager,
    private val apiUserService: ApiUserService
) : RemoteDataSource() {

    suspend fun login(username: String, password: String) {
        val response = handleApiResponse {
            apiUserService.login(NetworkCredentials(username, password))
        }
        sessionManager.saveAuthToken(response.token)
    }

    suspend fun logout() {
        handleApiResponse { apiUserService.logout() }
        sessionManager.removeAuthToken()
    }

    suspend fun getCurrentUser(): NetworkUser {
        return handleApiResponse { apiUserService.getCurrentUser() }
    }
}