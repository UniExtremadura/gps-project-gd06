package com.spotify.quavergd06

import com.spotify.quavergd06.data.model.Artist
import com.spotify.quavergd06.database.StringListWrapper
import org.junit.Test

import org.junit.Assert.*

class ArtistUnitTest {
    @Test
    fun testArtistProperties() {
        val artist = Artist(
            artistId = "123",
            name = "Artista Ejemplo",
            popularity = 85,
            timeRange = "short_term",
            position = 1,
            genres = StringListWrapper(arrayListOf("Pop", "Rock")),
            imageUrls = StringListWrapper(arrayListOf("url1", "url2"))
        )
        assertEquals("123", artist.artistId)
        assertEquals("Artista Ejemplo", artist.name)
        assertEquals(85, artist.popularity)
        assertEquals("short_term", artist.timeRange)
        assertEquals(1, artist.position)
        assertEquals(listOf("Pop", "Rock"), artist.genres.list)
        assertEquals(listOf("url1", "url2"), artist.imageUrls.list)
    }

    @Test
    fun testDefaultValues() {
        val artist = Artist(
            artistId = "456",
            name = null,
            popularity = null,
            timeRange = "short_term",
            position = null
        )

        assertEquals("456", artist.artistId)
        assertEquals(null, artist.name)
        assertEquals(null, artist.popularity)
        assertEquals("short_term", artist.timeRange)
        assertEquals(null, artist.position)
        assertEquals(emptyList<String>(), artist.genres.list)
        assertEquals(emptyList<String>(), artist.imageUrls.list)
    }


    //haz un test que pruebe el acceso a las cada una de las propiedades por separado en cada test
    @Test
    fun testArtistId() {
        val artist = Artist(
            artistId = "123",
            name = "Artista Ejemplo",
            popularity = 85,
            timeRange = "last_week",
            position = 1,
            genres = StringListWrapper(arrayListOf("Pop", "Rock")),
            imageUrls = StringListWrapper(arrayListOf("url1", "url2"))
        )
        assertEquals("123", artist.artistId)
    }

    @Test
    fun testName() {
        val artist = Artist(
            artistId = "123",
            name = "Artista Ejemplo",
            popularity = 85,
            timeRange = "last_week",
            position = 1,
            genres = StringListWrapper(arrayListOf("Pop", "Rock")),
            imageUrls = StringListWrapper(arrayListOf("url1", "url2"))
        )
        assertEquals("Artista Ejemplo", artist.name)
    }

    @Test
    fun testPopularity() {
        val artist = Artist(
            artistId = "123",
            name = "Artista Ejemplo",
            popularity = 85,
            timeRange = "last_week",
            position = 1,
            genres = StringListWrapper(arrayListOf("Pop", "Rock")),
            imageUrls = StringListWrapper(arrayListOf("url1", "url2"))
        )
        assertEquals(85, artist.popularity)
    }

    @Test
    fun testTimeRange() {
        val artist = Artist(
            artistId = "123",
            name = "Artista Ejemplo",
            popularity = 85,
            timeRange = "last_week",
            position = 1,
            genres = StringListWrapper(arrayListOf("Pop", "Rock")),
            imageUrls = StringListWrapper(arrayListOf("url1", "url2"))
        )
        assertEquals("last_week", artist.timeRange)
    }

    @Test
    fun testPosition() {
        val artist = Artist(
            artistId = "123",
            name = "Artista Ejemplo",
            popularity = 85,
            timeRange = "last_week",
            position = 1,
            genres = StringListWrapper(arrayListOf("Pop", "Rock")),
            imageUrls = StringListWrapper(arrayListOf("url1", "url2"))
        )
        assertEquals(1, artist.position)
    }

    @Test
    fun testGenres() {
        val artist = Artist(
            artistId = "123",
            name = "Artista Ejemplo",
            popularity = 85,
            timeRange = "last_week",
            position = 1,
            genres = StringListWrapper(arrayListOf("Pop", "Rock")),
            imageUrls = StringListWrapper(arrayListOf("url1", "url2"))
        )
        assertEquals(listOf("Pop", "Rock"), artist.genres.list)
    }

    @Test
    fun testImageUrls() {
        val artist = Artist(
            artistId = "123",
            name = "Artista Ejemplo",
            popularity = 85,
            timeRange = "last_week",
            position = 1,
            genres = StringListWrapper(arrayListOf("Pop", "Rock")),
            imageUrls = StringListWrapper(arrayListOf("url1", "url2"))
        )
        assertEquals(listOf("url1", "url2"), artist.imageUrls.list)
    }

}