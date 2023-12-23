package com.spotify.quavergd06.view.home.stats.topArtistTracks.topArtist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.spotify.quavergd06.QuaverApplication
import com.spotify.quavergd06.api.setKey
import com.spotify.quavergd06.data.ArtistsRepository
import com.spotify.quavergd06.data.TracksRepository
import com.spotify.quavergd06.data.model.Artist
import com.spotify.quavergd06.data.model.Track
import com.spotify.quavergd06.data.model.User
import kotlinx.coroutines.launch

class TopArtistGridAllViewModel (
    repository: ArtistsRepository
) : ViewModel() {

    var user: User? = null

    private val _artist = repository.artistsAll
    val artists: LiveData<List<Artist>> get() = _artist

    init {
        viewModelScope.launch {
            user?.let {
                setKey(it.token)
            }
            repository.tryUpdateRecentCache("long_term")
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
                return TopArtistGridAllViewModel((application as QuaverApplication).appContainer.artistRepository) as T
            }
        }
    }
}