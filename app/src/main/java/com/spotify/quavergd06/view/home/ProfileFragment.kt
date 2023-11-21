package com.spotify.quavergd06.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.spotify.quavergd06.R
import com.spotify.quavergd06.databinding.FragmentProfileBinding
import com.spotify.quavergd06.view.LoginActivity
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
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

        // Obtén la ImageView
        val pictureImageView = binding.AvatarImageView

        // con picaso muestra la imagen de perfil del usuario que se el drawable spotify_logo.png
        Picasso.get().load(R.drawable.spotify_logo).into(pictureImageView)

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

    private fun getUserName():String {
        return "User"
    }
}