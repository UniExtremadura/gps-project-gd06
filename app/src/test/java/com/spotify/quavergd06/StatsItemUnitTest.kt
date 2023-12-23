package com.spotify.quavergd06

import com.spotify.quavergd06.data.model.StatsItem
import com.spotify.quavergd06.database.StringListWrapper
import org.junit.Assert.assertEquals
import org.junit.Test

class StatsItemUnitTest {
    @Test
    fun testStatsItemProperties() {
        val statsItem = StatsItem(
            id = "123",
            name = "Cancion Ejemplo",
            imageUrls = arrayListOf("url1", "url2"),
            genres = arrayListOf("Pop", "Rock"),
            popularity = 85,
            artistId = "456",
            artistName = "Artista Ejemplo",
            album = "Album Ejemplo",
        )

        assertEquals("123", statsItem.id)
        assertEquals("Cancion Ejemplo", statsItem.name)
        assertEquals(arrayListOf("url1", "url2"), statsItem.imageUrls)
        assertEquals(arrayListOf("Pop", "Rock"), statsItem.genres)
        assertEquals(85, statsItem.popularity)
        assertEquals("456", statsItem.artistId)
        assertEquals("Artista Ejemplo", statsItem.artistName)
        assertEquals("Album Ejemplo", statsItem.album)
    }

    @Test
    fun testStatsItemDefaultValues() {
        val statsItem = StatsItem()

        assertEquals("33", statsItem.id)
        assertEquals(null, statsItem.name)
        assertEquals(null, statsItem.imageUrls)
        assertEquals(arrayListOf<String>(), statsItem.genres)
        assertEquals(null, statsItem.popularity)
        assertEquals(null, statsItem.artistId)
        assertEquals(null, statsItem.artistName)
        assertEquals(null, statsItem.album)
    }

    @Test
    fun testStatsItemId() {
        val statsItem = StatsItem(id = "123")

        assertEquals("123", statsItem.id)
    }

    @Test
    fun testStatsItemName() {
        val statsItem = StatsItem(name = "Cancion Ejemplo")

        assertEquals("Cancion Ejemplo", statsItem.name)
    }

    @Test
    fun testStatsItemImageUrls() {
        val statsItem = StatsItem(imageUrls = arrayListOf("url1", "url2"))

        assertEquals(arrayListOf("url1", "url2"), statsItem.imageUrls)
    }

    @Test
    fun testStatsItemGenres() {
        val statsItem = StatsItem(genres = arrayListOf("Pop", "Rock"))

        assertEquals(arrayListOf("Pop", "Rock"), statsItem.genres)
    }

    @Test
    fun testStatsItemPopularity() {
        val statsItem = StatsItem(popularity = 85)

        assertEquals(85, statsItem.popularity)
    }

    @Test
    fun testStatsItemArtistId() {
        val statsItem = StatsItem(artistId = "456")

        assertEquals("456", statsItem.artistId)
    }

    @Test
    fun testStatsItemArtistName() {
        val statsItem = StatsItem(artistName = "Artista Ejemplo")

        assertEquals("Artista Ejemplo", statsItem.artistName)
    }

    @Test
    fun testStatsItemAlbum() {
        val statsItem = StatsItem(album = "Album Ejemplo")

        assertEquals("Album Ejemplo", statsItem.album)
    }

}