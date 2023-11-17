package com.spotify.quavergd06.api

import com.spotify.quavergd06.data.api.ArtistResponse
import com.spotify.quavergd06.data.api.TrackResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
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

class APIException(message: String, cause: Throwable?) : Throwable(message, cause)

interface SpotifyApiService {
    // Endpoint para buscar artistas en Spotify
    @Headers("Content-Type: application/json")
    @GET("me/top/artists")
    suspend fun loadTopArtists(
        @Query("time_range") timeRange: String,
        @Query("limit") limit: Int = 50
    ): Response<ArtistResponse>

    // Endpoint para buscar canciones en Spotify
    @Headers("Content-Type: application/json")
    @GET("me/top/tracks")
    suspend fun loadTopTracks(
        @Query("time_range") timeRange: String,
        @Query("limit") limit: Int = 50
    ): Response<TrackResponse>
}

interface APICallback {
    fun onCompleted()
    fun onError(cause: Throwable)
}