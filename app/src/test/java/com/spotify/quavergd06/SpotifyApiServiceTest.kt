package com.spotify.quavergd06

import com.spotify.quavergd06.api.SpotifyApiService
import com.spotify.quavergd06.data.api.ApiImage
import com.spotify.quavergd06.data.api.ArtistItem
import com.spotify.quavergd06.data.api.TrackItem
import com.spotify.quavergd06.data.api.UserProfileInfoResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SpotifyApiServiceTest {

    @Mock
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup(){
        mockWebServer = MockWebServer()
    }

    @Test
    fun getUserProfileTest(){
        val userProfileInfoResponse = UserProfileInfoResponse(
            id = "33",
            displayName = "Usuario Ejemplo",
            images = arrayListOf(ApiImage("url1", 1, 1),
                ApiImage("url1", 1, 1))
        )

        val mockResponse =
            MockResponse().addHeader("UserProfile", "application/json; charset=utf-8")
                .setBody("{\"UserProfile\": \"$userProfileInfoResponse\"}")

        mockWebServer.enqueue(mockResponse)
        mockWebServer.start()

        val baseUrl = mockWebServer.url("/getUserProfile")

        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create()).client(client)
                .build()

            val call = retrofit.create(SpotifyApiService::class.java).getUserProfile()
            assertEquals(userProfileInfoResponse,call.body())
        }
        mockWebServer.shutdown()
    }
}