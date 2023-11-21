package com.spotify.quavergd06.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.spotify.quavergd06.R
import com.spotify.quavergd06.api.APIException
import com.spotify.quavergd06.api.getNetworkService
import com.spotify.quavergd06.api.setKey
import com.spotify.quavergd06.data.api.UserProfileInfoResponse
import com.spotify.quavergd06.databinding.FragmentProfileBinding
import com.spotify.quavergd06.view.LoginActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private var userInfo: UserProfileInfoResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        val logoutButton = binding.unlogButton
        logoutButton.setOnClickListener {
            // Borrar el token almacenado
            clearUserToken()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            setKey(obtenerSpotifyApiKey(requireContext())!!)
            fetchUserInfo()
        }

            // Obtén la ImageView
        val pictureImageView = binding.AvatarImageView

        // con picaso muestra la imagen de perfil del usuario que se el drawable spotify_logo.png
        Picasso.get().load(getUserPicture()).into(pictureImageView)

        // Obtén el TextView
        val usernameTextView = binding.usernameTextView
        // Obtén el nombre de usuario utilizando la función getUsername()
        val username = getUserName()
        // Establece el nombre de usuario en el TextView
        usernameTextView.text = username
    }


    private fun clearUserToken() {
        val sharedPreferences =
            requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }

    private fun getUserName() : String {
        return userInfo?.displayName ?: "User"
    }

    private fun getUserPicture(): String {
        // profile picture o el drawable spotify_logo
        return userInfo?.images?.get(0)?.url ?: "drawable://spotify_logo"
    }

    private suspend fun fetchUserInfo() {

        try {
            userInfo = getNetworkService().getUserProfile().body()
        } catch (cause: Throwable) {
            throw APIException("Unable to fetch data from API", cause)
        }
    }

    private fun obtenerSpotifyApiKey(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("access_token", null)
    }
}