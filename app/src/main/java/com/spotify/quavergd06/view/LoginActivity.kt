package com.spotify.quavergd06.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.spotify.quavergd06.R
import com.spotify.quavergd06.databinding.ActivityLoginBinding
import com.spotify.quavergd06.view.home.HomeActivity
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse

class LoginActivity : AppCompatActivity() {

    private val redirectUri: Uri
        get() = Uri.Builder()
            .scheme(getString(R.string.com_spotify_sdk_redirect_scheme))
            .authority(getString(R.string.com_spotify_sdk_redirect_host))
            .build()

    companion object {
        const val CLIENT_ID = "e14071702cea4db5bf9255924ec9e882"
        const val AUTH_TOKEN_REQUEST_CODE = 0x10
    }

    private var mAccessToken: String? = null

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val preferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val storedToken = preferences.getString("access_token", null)

        if (storedToken != null) {
            // Access token is available, start the HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)

            // Finish the LoginActivity to prevent the user from navigating back to it
            finish()
            return
        }

        supportActionBar?.hide()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    fun onRequestTokenClicked(view: View?) {
        val request = getAuthenticationRequestToken()
        AuthorizationClient.openLoginActivity(this, AUTH_TOKEN_REQUEST_CODE, request)
    }

    private fun getAuthenticationRequestToken(): AuthorizationRequest {
        return AuthorizationRequest.Builder(
            CLIENT_ID,
            AuthorizationResponse.Type.TOKEN,
            redirectUri.toString()
        )
            .setShowDialog(false)
            .setScopes(arrayOf("user-read-email"))
            .setCampaign("your-campaign-token")
            .build()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val response = AuthorizationClient.getResponse(resultCode, data)

        if (requestCode == AUTH_TOKEN_REQUEST_CODE) {
            mAccessToken = response.accessToken

            persistToken()

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)

            // Finish the LoginActivity to prevent the user from navigating back to it
            finish()
        }
    }

    private fun persistToken() {
        val preferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        preferences.edit().putString("access_token", mAccessToken).apply()
    }
}