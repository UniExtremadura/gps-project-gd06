/*
 * Copyright (c) 2015-2018 Spotify AB
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.spotify.quavergd06.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.spotify.quavergd06.R

import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse

import com.spotify.quavergd06.databinding.ActivityLoginBinding
import com.spotify.quavergd06.view.home.HomeActivity

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
        return AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, redirectUri.toString())
            .setShowDialog(false)
            .setScopes(arrayOf("user-read-email", "user-top-read"))
            .setCampaign("your-campaign-token")
            .build()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val response = AuthorizationClient.getResponse(resultCode, data)

        if (requestCode == AUTH_TOKEN_REQUEST_CODE) {
            mAccessToken = response.accessToken

            if (mAccessToken == null)
                return

            persistToken()

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)

            // Finish the LoginActivity to prevent the user from navigating back to it
            finish()

        }
    }

    private fun persistToken() {
        val preferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        preferences.edit().putString("access_token", mAccessToken).apply()
    }

}