package com.spotify.quavergd06

import com.spotify.quavergd06.data.model.Moment
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class MomentUnitTest {
    @Test
    fun testMomentProperties() {
        val moment = Moment(
            momentId = 33,
            title = "Momento Ejemplo",
            date = Date(1,1,1),
            songTitle = "Cancion Ejemplo",
            imageURI = "url1",
            location = "Lugar Ejemplo",
            latitude = 1.0,
            longitude = 1.0
        )

        assertEquals(33L, moment.momentId)
        assertEquals("Momento Ejemplo", moment.title)
        assertEquals(Date(1,1,1), moment.date)
        assertEquals("Cancion Ejemplo", moment.songTitle)
        assertEquals("url1", moment.imageURI)
        assertEquals("Lugar Ejemplo", moment.location)
        assertEquals(1.0, moment.latitude, 0.0)
        assertEquals(1.0, moment.longitude, 0.0)
    }

    @Test
    fun testDefaultValues() {
        val moment = Moment(
            momentId = null,
            date = null,
            imageURI = "url1",
            latitude = 1.0,
            longitude = 1.0
        )

        assertEquals(null, moment.momentId)
        assertEquals("", moment.title)
        assertEquals(null, moment.date)
        assertEquals("", moment.songTitle)
        assertEquals("url1", moment.imageURI)
        assertEquals("", moment.location)
        assertEquals(1.0, moment.latitude, 0.0)
        assertEquals(1.0, moment.longitude, 0.0)
    }

    @Test
    fun testMomentId() {
        val moment = Moment(
            momentId = 33,
            title = "Momento Ejemplo",
            date = Date(1,1,1),
            songTitle = "Cancion Ejemplo",
            imageURI = "url1",
            location = "Lugar Ejemplo",
            latitude = 1.0,
            longitude = 1.0
        )
        assertEquals(33L, moment.momentId)
    }

    @Test
    fun testTitle() {
        val moment = Moment(
            momentId = 33,
            title = "Momento Ejemplo",
            date = Date(1,1,1),
            songTitle = "Cancion Ejemplo",
            imageURI = "url1",
            location = "Lugar Ejemplo",
            latitude = 1.0,
            longitude = 1.0
        )
        assertEquals("Momento Ejemplo", moment.title)
    }

    @Test
    fun testDate() {
        val moment = Moment(
            momentId = 33,
            title = "Momento Ejemplo",
            date = Date(1,1,1),
            songTitle = "Cancion Ejemplo",
            imageURI = "url1",
            location = "Lugar Ejemplo",
            latitude = 1.0,
            longitude = 1.0
        )
        assertEquals(Date(1,1,1), moment.date)
    }

    @Test
    fun testSongTitle() {
        val moment = Moment(
            momentId = 33,
            title = "Momento Ejemplo",
            date = Date(1,1,1),
            songTitle = "Cancion Ejemplo",
            imageURI = "url1",
            location = "Lugar Ejemplo",
            latitude = 1.0,
            longitude = 1.0
        )
        assertEquals("Cancion Ejemplo", moment.songTitle)
    }

    @Test
    fun testImageURI() {
        val moment = Moment(
            momentId = 33,
            title = "Momento Ejemplo",
            date = Date(1,1,1),
            songTitle = "Cancion Ejemplo",
            imageURI = "url1",
            location = "Lugar Ejemplo",
            latitude = 1.0,
            longitude = 1.0
        )
        assertEquals("url1", moment.imageURI)
    }

    @Test
    fun testLocation() {
        val moment = Moment(
            momentId = 33,
            title = "Momento Ejemplo",
            date = Date(1,1,1),
            songTitle = "Cancion Ejemplo",
            imageURI = "url1",
            location = "Lugar Ejemplo",
            latitude = 1.0,
            longitude = 1.0
        )
        assertEquals("Lugar Ejemplo", moment.location)
    }

    @Test
    fun testLatitude() {
        val moment = Moment(
            momentId = 33,
            title = "Momento Ejemplo",
            date = Date(1,1,1),
            songTitle = "Cancion Ejemplo",
            imageURI = "url1",
            location = "Lugar Ejemplo",
            latitude = 1.0,
            longitude = 1.0
        )
        assertEquals(1.0, moment.latitude, 0.0)
    }

    @Test
    fun testLongitude() {
        val moment = Moment(
            momentId = 33,
            title = "Momento Ejemplo",
            date = Date(1,1,1),
            songTitle = "Cancion Ejemplo",
            imageURI = "url1",
            location = "Lugar Ejemplo",
            latitude = 1.0,
            longitude = 1.0
        )
        assertEquals(1.0, moment.longitude, 0.0)
    }

}