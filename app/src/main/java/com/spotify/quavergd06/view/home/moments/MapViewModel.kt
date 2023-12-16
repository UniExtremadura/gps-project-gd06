package com.spotify.quavergd06.view.home.moments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.spotify.quavergd06.QuaverApplication
import com.spotify.quavergd06.data.MomentsRepository

class MapViewModel(private val repository: MomentsRepository)
    : ViewModel() {
    val moments = repository.moments

    companion object {
        val Factory : ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T { // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return MapViewModel( (application as QuaverApplication).appContainer.momentsRepository, ) as T }
        }
    }
}