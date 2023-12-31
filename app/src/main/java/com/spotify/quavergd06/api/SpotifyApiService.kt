package com.spotify.quavergd06.api

import com.spotify.quavergd06.data.api.ArtistItem
import com.spotify.quavergd06.data.api.HistoryResponse
import com.spotify.quavergd06.data.api.Tracks
import com.spotify.quavergd06.data.api.TopArtistsResponse
import com.spotify.quavergd06.data.api.TopGlobalApiResponse
import com.spotify.quavergd06.data.api.TopTracksResponse
import com.spotify.quavergd06.data.api.TrackItem
import com.spotify.quavergd06.data.api.UserProfileInfoResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.spotify.com/v1/"

private lateinit var SPOTIFY_API_KEY: String
fun setKey(key: String){
    SPOTIFY_API_KEY = key
}

// Define el servicio de Spotify
val spotifyService: SpotifyApiService by lazy {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor())
        .addInterceptor { chain ->
            // Agrega el token de usuario al encabezado de autorización
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $SPOTIFY_API_KEY")
                .build()
            chain.proceed(request)
        }
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    retrofit.create(SpotifyApiService::class.java)
}
fun getNetworkService() = spotifyService

class APIError(message: String, cause: Throwable?) : Throwable(message, cause)

interface SpotifyApiService {
    // Endpoint para buscar artistas en Spotify
    @Headers("Content-Type: application/json")
    @GET("me/top/artists")
    suspend fun loadTopArtists(
        @Query("time_range") timeRange: String,
        @Query("limit") limit: Int = 30
    ): Response<TopArtistsResponse>

    @Headers("Content-Type: application/json")
    @GET("me/top/tracks")
    suspend fun loadTopTracks(
        @Query("time_range") timeRange: String,
        @Query("limit") limit: Int = 30
    ): Response<TopTracksResponse>

    @Headers("Content-Type: application/json")
    @GET("artists/{id}")
    suspend fun loadArtist(
        @Path("id") id: String
    ): Response<ArtistItem>

    @Headers("Content-Type: application/json")
    @GET("tracks/{id}")
    suspend fun loadTrack(
        @Path("id") id: String
    ): Response<TrackItem>


    @Headers("Content-Type: application/json")
    @GET("me/player/recently-played")
    suspend fun loadHistory(
        @Query("limit") limit: Int = 10,
        @Query("before") before: String = System.currentTimeMillis().toString()
    ): Response<HistoryResponse>

    @Headers("Content-Type: application/json")
    @GET("https://api.spotify.com/v1/playlists/37i9dQZEVXbMDoHDwVN2tF")
    suspend fun loadTopGlobal(
        @Query("market") market: String = "ES"
    ): Response<TopGlobalApiResponse>

    @Headers("Content-Type: application/json")
    @GET("me/top/tracks?limit=20&offset=0")
    suspend fun loadTracks(): Response<Tracks>

    @Headers("Content-Type: application/json")
    @GET("me")
    suspend fun getUserProfile(): Response<UserProfileInfoResponse>

}

interface APICallback {
    fun onCompleted()
    fun onError(cause: Throwable)
}