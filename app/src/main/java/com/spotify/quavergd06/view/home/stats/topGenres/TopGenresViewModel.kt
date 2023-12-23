package com.spotify.quavergd06.view.home.stats.topGenres

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.spotify.quavergd06.QuaverApplication
import com.spotify.quavergd06.api.setKey
import com.spotify.quavergd06.data.ArtistsRepository
import com.spotify.quavergd06.data.model.Artist
import com.spotify.quavergd06.data.model.User
import kotlinx.coroutines.launch

class TopGenresViewModel(
    repository: ArtistsRepository
) : ViewModel() {

    var user: User? = null

    private val _artists = repository.artists
    private val _genres = MutableLiveData<List<String>>()
    val genres: LiveData<List<String>>? get() = _genres

    init {
        viewModelScope.launch {
            user?.let {
                setKey(it.token)
            }
            repository.tryUpdateRecentCache("short_term")
            generateGenresList()
        }
    }

    private fun generateGenresList() {
        _artists.value?.let { artists ->
            val genresList = getGenresFromArtistList(artists)
            val topGenres = getTopGenres(genresList)
            _genres.value = topGenres
        }
    }

    private fun getGenresFromArtistList(artists: List<Artist>): List<String> {
        return artists.flatMap { it.genres.list }
    }

    private fun getTopGenres(genresList: List<String>): List<String> {
        return genresList.groupingBy { it }.eachCount().toList().sortedByDescending { (_, value) -> value }.map { (key, _) -> key }
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
                return TopGenresViewModel((application as QuaverApplication).appContainer.artistRepository) as T
            }
        }
    }
}