package com.spotify.quavergd06

import com.spotify.quavergd06.data.model.User
import com.spotify.quavergd06.database.StringListWrapper
import org.junit.Assert.assertEquals
import org.junit.Test

class UserUnitTest {

    @Test
    fun testUserProperties() {
        val user = User(
            userId = "123",
            name = "Usuario Ejemplo",
            token = "Token Ejemplo",
            profileImages = StringListWrapper(arrayListOf("url1", "url2"))
        )

        assertEquals("123", user.userId)
        assertEquals("Usuario Ejemplo", user.name)
        assertEquals("Token Ejemplo", user.token)
        assertEquals(arrayListOf("url1", "url2"), user.profileImages.list)
    }

    @Test
    fun testUserDefaultValues() {
        val user = User()

        assertEquals("", user.userId)
        assertEquals("", user.name)
        assertEquals("", user.token)
        assertEquals(arrayListOf<String>(), user.profileImages.list)
    }

    @Test
    fun testUserId() {
        val user = User(userId = "123")

        assertEquals("123", user.userId)
    }

    @Test
    fun testUserName() {
        val user = User(name = "Usuario Ejemplo")

        assertEquals("Usuario Ejemplo", user.name)
    }

    @Test
    fun testToken(){
        val user = User(token = "Token Ejemplo")

        assertEquals("Token Ejemplo", user.token)
    }

    @Test
    fun testProfileImages(){
        val user = User(profileImages = StringListWrapper(arrayListOf("url1", "url2")))

        assertEquals(arrayListOf("url1", "url2"), user.profileImages.list)
    }

}
