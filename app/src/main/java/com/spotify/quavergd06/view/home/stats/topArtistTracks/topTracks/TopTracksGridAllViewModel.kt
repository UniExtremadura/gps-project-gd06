package com.spotify.quavergd06.view.home.stats.preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.spotify.quavergd06.QuaverApplication
import com.spotify.quavergd06.api.setKey
import com.spotify.quavergd06.data.TracksRepository
import com.spotify.quavergd06.data.model.Track
import com.spotify.quavergd06.data.model.User
import kotlinx.coroutines.launch

class TopTracksGridAllViewModel (
    repository: TracksRepository
) : ViewModel() {

    var user: User? = null

    private val _tracks = repository.tracksAll
    val tracks: LiveData<List<Track>> get() = _tracks

    init {
        viewModelScope.launch {
            user?.let {
                setKey(it.token)
            }
            repository.tryUpdateCache("long_term")
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T { // Get the Application object from extras
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return TopTracksGridAllViewModel((application as QuaverApplication).appContainer.tracksRepository) as T
            }
        }
    }
}