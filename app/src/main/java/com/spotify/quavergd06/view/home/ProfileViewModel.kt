package com.spotify.quavergd06.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.spotify.quavergd06.QuaverApplication
import com.spotify.quavergd06.api.setKey
import com.spotify.quavergd06.data.UserRepository
import com.spotify.quavergd06.data.model.User
import kotlinx.coroutines.launch

class ProfileViewModel (
    private val userRepository: UserRepository
) : ViewModel() {
    private val _user = userRepository.user
    val user: LiveData<User> get() = _user

    var auxUser: User? = null
    init {
        viewModelScope.launch {
            auxUser?.let {
                setKey(it.token)
            }
            userRepository.tryUpdateCache()
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            userRepository.deleteUser()
        }
    }

    companion object {
        val Factory : ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T { // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return ProfileViewModel((application as QuaverApplication).appContainer.userRepository) as T }
        }
    }
}