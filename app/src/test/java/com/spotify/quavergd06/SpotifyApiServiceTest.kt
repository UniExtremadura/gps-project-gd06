package com.spotify.quavergd06

import com.spotify.quavergd06.api.SpotifyApiService
import com.spotify.quavergd06.data.api.ApiImage
import com.spotify.quavergd06.data.api.ArtistItem
import com.spotify.quavergd06.data.api.HistoryResponse
import com.spotify.quavergd06.data.api.ItemsHistory
import com.spotify.quavergd06.data.api.TopArtistsResponse
import com.spotify.quavergd06.data.api.TopGlobalApiResponse
import com.spotify.quavergd06.data.api.TopGlobalTracks
import com.spotify.quavergd06.data.api.TopTracksResponse
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

    @Mock
    private lateinit var artistItem: ArtistItem

    @Mock
    private lateinit var trackItem: TrackItem

    @Before
    fun setup(){
        mockWebServer = MockWebServer()
        artistItem = ArtistItem(
            id = "1",
            name = "Artista Ejemplo",
            popularity = 85,
            genres = arrayListOf("Pop", "Rock"),
            images = arrayListOf(ApiImage("url1", 1, 1),
                ApiImage("url1", 1, 1))
        )
        trackItem = TrackItem(
            id = "1",
            name = "Cancion Ejemplo",
            artists = arrayListOf(artistItem),
            album = null,
        )
    }

    @Test
    fun loadTopArtistTest(){
        val artistResponse = TopArtistsResponse(artistItems = ArrayList<ArtistItem>().apply {
            repeat(30) { index ->
                val artist = artistItem.copy(id = (index + 1).toString())
                add(artist)
            }
        })

        val mockResponse =
            MockResponse().addHeader("ArtistResponse", "application/json; charset=utf-8")
                .setBody("{\"Artist\": \"$artistResponse\"}")

        mockWebServer.enqueue(mockResponse)
        mockWebServer.start()

        val baseUrl = mockWebServer.url("/getTopArtists")

        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create()).client(client)
                .build()

            val call = retrofit.create(SpotifyApiService::class.java).loadTopArtists("long_term")
            assertEquals(artistResponse,call.body())
        }
        mockWebServer.shutdown()
    }

    @Test
    fun loadTopTracksTest(){
        val trackResponse = TopTracksResponse(trackItems = ArrayList<TrackItem>().apply {
            repeat(30) { index ->
                val track = trackItem.copy(id = (index + 1).toString())
                add(track)
            }
        })

        val mockResponse =
            MockResponse().addHeader("TrackResponse", "application/json; charset=utf-8")
                .setBody("{\"Track\": \"$trackResponse\"}")

        mockWebServer.enqueue(mockResponse)
        mockWebServer.start()

        val baseUrl = mockWebServer.url("/getTopTracks")

        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create()).client(client)
                .build()

            val call = retrofit.create(SpotifyApiService::class.java).loadTopArtists("long_term")
            assertEquals(trackResponse,call.body())

        }
        mockWebServer.shutdown()
    }

    @Test
    fun loadArtistTest(){
        val mockResponse =
            MockResponse().addHeader("ArtistResponse", "application/json; charset=utf-8")
                .setBody("{\"Artist\": \"$artistItem\"}")

        mockWebServer.enqueue(mockResponse)
        mockWebServer.start()

        val baseUrl = mockWebServer.url("/getArtist")

        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create()).client(client)
                .build()

            val call = retrofit.create(SpotifyApiService::class.java).loadArtist("1")
            assertEquals(artistItem,call.body())
        }
        mockWebServer.shutdown()
    }

    @Test
    fun loadTrackTest(){
        val mockResponse =
            MockResponse().addHeader("TrackResponse", "application/json; charset=utf-8")
                .setBody("{\"Track\": \"$trackItem\"}")

        mockWebServer.enqueue(mockResponse)
        mockWebServer.start()

        val baseUrl = mockWebServer.url("/getTrack")

        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create()).client(client)
                .build()

            val call = retrofit.create(SpotifyApiService::class.java).loadTrack("1")
            assertEquals(trackItem,call.body())
        }
        mockWebServer.shutdown()
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
