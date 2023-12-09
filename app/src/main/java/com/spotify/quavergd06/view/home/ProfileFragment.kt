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
import com.spotify.quavergd06.api.APIError
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

    private var userInfo: UserProfileInfoResponse = UserProfileInfoResponse()

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
            // Cerrar el fragmento actual
            requireActivity().finish()
            // Iniciar LoginActivity
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            setKey(obtenerSpotifyApiKey(requireContext())!!)
            fetchUserInfo()
            setupUI()
        }
    }

    private fun setupUI() {
        with(binding) {
        try {
             Picasso.get().load(userInfo?.images?.get(0)?.url).placeholder(R.drawable.user).into(AvatarImageView)
            }
            catch (e: Exception){
                Picasso.get().load(R.drawable.user).into(AvatarImageView)
            }
            usernameTextView.text = userInfo.displayName
        }
    }


    private fun clearUserToken() {
        val sharedPreferences =
            requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }

    private suspend fun fetchUserInfo() {
        try {
            userInfo = getNetworkService().getUserProfile().body() ?: UserProfileInfoResponse()
        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
    }

    private fun obtenerSpotifyApiKey(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("access_token", null)
    }
}