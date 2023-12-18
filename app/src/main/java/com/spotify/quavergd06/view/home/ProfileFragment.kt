package com.spotify.quavergd06.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.spotify.quavergd06.R
import com.spotify.quavergd06.databinding.FragmentProfileBinding
import com.spotify.quavergd06.view.LoginActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels { ProfileViewModel.Factory }
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        val logoutButton = binding.unlogButton
        logoutButton.setOnClickListener {
            // Borrar el token almacenado
            clearUserToken()
            viewModel.user.value?.userId?.let { viewModel.deleteUser() }
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

        homeViewModel.user.observe(viewLifecycleOwner) { user ->
            viewModel.auxUser = user
        }
        viewModel.user.observe(viewLifecycleOwner) {
            setupUI()
        }

    }

    private fun setupUI() {
        with(binding) {
            try {
                Picasso.get().load(viewModel.user.value?.profileImages?.list?.get(0)).placeholder(R.drawable.user).into(AvatarImageView)
            } catch (e: Exception){
                    Picasso.get().load(R.drawable.user).into(AvatarImageView)
                }
            usernameTextView.text = viewModel.user.value?.name
            }
    }


    private fun clearUserToken() {
        val sharedPreferences =
            requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }

}