package com.spotify.quavergd06.data
import com.spotify.quavergd06.api.APIError
import com.spotify.quavergd06.api.SpotifyApiService
import com.spotify.quavergd06.database.dao.UserDAO

class UserRepository (
    private val userDAO: UserDAO,
    private val networkService: SpotifyApiService) {

    private var lastUpdateTimeMillis: Long = 0L
    val user = userDAO.getUser()

    suspend fun tryUpdateCache() {
        if (shouldUpdateCache())
            fetchUser()
    }

    private suspend fun fetchUser() {
        try {
            val user = networkService.getUserProfile().body()?.toUser()
            if (user != null) {
                userDAO.insertUser(user)
                lastUpdateTimeMillis = System.currentTimeMillis ()
            }
        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
    }

    suspend fun deleteUser() {
        userDAO.deleteUser()
    }

    private fun shouldUpdateCache(): Boolean {
        val lastFetchTimeMillis = lastUpdateTimeMillis
        val timeFromLastFetch = System.currentTimeMillis() - lastFetchTimeMillis
        return timeFromLastFetch > MIN_TIME_FROM_LAST_FETCH_MILLIS
    }

    companion object {
        private const val MIN_TIME_FROM_LAST_FETCH_MILLIS: Long = 60000
    }
}