package com.spotify.quavergd06

import com.spotify.quavergd06.data.model.Track
import com.spotify.quavergd06.database.StringListWrapper
import org.junit.Assert.assertEquals
import org.junit.Test

class TrackUnitTest {

    @Test
    fun testTrackProperties() {
        val track = Track(
            trackId = "123",
            name = "Cancion Ejemplo",
            album = "Album Ejemplo",
            artistId = "123",
            artistName = "Artista Ejemplo",
            position = 1,
            timeRange = "short_term",
            type = "personal",
        )

        assertEquals("123", track.trackId)
        assertEquals("Cancion Ejemplo", track.name)
        assertEquals("Album Ejemplo", track.album)
        assertEquals("123", track.artistId)
        assertEquals("Artista Ejemplo", track.artistName)
        assertEquals(1, track.position)
        assertEquals("short_term", track.timeRange)
        assertEquals("personal", track.type)
    }

    @Test
    fun testDefaultValues() {
        val track = Track(
            trackId = "456",
            name = null,
            album = null,
            artistId = null,
            artistName = null,
            position = null,
            timeRange = "short_term",
            type = "personal",
            imageUrls = StringListWrapper(arrayListOf("url1", "url2"))
        )

        assertEquals("456", track.trackId)
        assertEquals(null, track.name)
        assertEquals(null, track.album)
        assertEquals(null, track.artistId)
        assertEquals(null, track.artistName)
        assertEquals(null, track.position)
        assertEquals("short_term", track.timeRange)
        assertEquals("personal", track.type)
        assertEquals(listOf("url1", "url2"), track.imageUrls.list)
    }

    @Test
    fun testTrackId() {
        val track = Track(
            trackId = "123",
            name = "Cancion Ejemplo",
            album = "Album Ejemplo",
            artistId = "123",
            artistName = "Artista Ejemplo",
            position = 1,
            timeRange = "short_term",
            type = "personal",
        )

        assertEquals("123", track.trackId)
    }

    @Test
    fun testName() {
        val track = Track(
            trackId = "123",
            name = "Cancion Ejemplo",
            album = "Album Ejemplo",
            artistId = "123",
            artistName = "Artista Ejemplo",
            position = 1,
            timeRange = "short_term",
            type = "personal",
        )

        assertEquals("Cancion Ejemplo", track.name)
    }

    @Test
    fun testAlbum() {
        val track = Track(
            trackId = "123",
            name = "Cancion Ejemplo",
            album = "Album Ejemplo",
            artistId = "123",
            artistName = "Artista Ejemplo",
            position = 1,
            timeRange = "short_term",
            type = "personal",
        )

        assertEquals("Album Ejemplo", track.album)
    }

    @Test
    fun testArtistId() {
        val track = Track(
            trackId = "123",
            name = "Cancion Ejemplo",
            album = "Album Ejemplo",
            artistId = "123",
            artistName = "Artista Ejemplo",
            position = 1,
            timeRange = "short_term",
            type = "personal",
        )

        assertEquals("123", track.artistId)
    }

    @Test
    fun testArtistName() {
        val track = Track(
            trackId = "123",
            name = "Cancion Ejemplo",
            album = "Album Ejemplo",
            artistId = "123",
            artistName = "Artista Ejemplo",
            position = 1,
            timeRange = "short_term",
            type = "personal",
        )

        assertEquals("Artista Ejemplo", track.artistName)
    }

    @Test
    fun testPosition() {
        val track = Track(
            trackId = "123",
            name = "Cancion Ejemplo",
            album = "Album Ejemplo",
            artistId = "123",
            artistName = "Artista Ejemplo",
            position = 1,
            timeRange = "short_term",
            type = "personal",
        )

        assertEquals(1, track.position)
    }

    @Test
    fun testTimeRange() {
        val track = Track(
            trackId = "123",
            name = "Cancion Ejemplo",
            album = "Album Ejemplo",
            artistId = "123",
            artistName = "Artista Ejemplo",
            position = 1,
            timeRange = "short_term",
            type = "personal",
        )

        assertEquals("short_term", track.timeRange)
    }

    @Test
    fun testType() {
        val track = Track(
            trackId = "123",
            name = "Cancion Ejemplo",
            album = "Album Ejemplo",
            artistId = "123",
            artistName = "Artista Ejemplo",
            position = 1,
            timeRange = "short_term",
            type = "personal",
        )

        assertEquals("personal", track.type)
    }

    @Test
    fun testImageUrls() {
        val track = Track(
            trackId = "123",
            name = "Cancion Ejemplo",
            album = "Album Ejemplo",
            artistId = "123",
            artistName = "Artista Ejemplo",
            timeRange = "short_term",
            type = "personal",
            imageUrls = StringListWrapper(arrayListOf("url1", "url2"))
        )

        assertEquals(arrayListOf("url1", "url2"), track.imageUrls.list)
    }

}