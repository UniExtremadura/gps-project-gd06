package com.spotify.quavergd06.view.home.stats.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.spotify.quavergd06.QuaverApplication
import com.spotify.quavergd06.api.APIError
import com.spotify.quavergd06.api.setKey
import com.spotify.quavergd06.data.ArtistsRepository
import com.spotify.quavergd06.data.model.StatsItem
import com.spotify.quavergd06.data.model.User
import com.spotify.quavergd06.data.toStatsItem
import kotlinx.coroutines.launch

class ArtistInfoViewModel(
    private val repository: ArtistsRepository
) : ViewModel() {

    private val _artistDetail = MutableLiveData<StatsItem>(null)
    val artistDetail: LiveData<StatsItem>
        get() = _artistDetail

    private val _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
        get() = _toast

    var user: User? = null
    var artist: StatsItem? = null
        set(value) {
            field = value
            getArtist()
        }

    private fun getArtist() {
        if (artist != null)
            viewModelScope.launch {
                try {
                    val _artist = repository.fetchArtistDetail(artist?.id!!)
                    _artistDetail.value = _artist.toStatsItem()
                } catch (error: APIError) {
                    _toast.value = error.message
                }
            }
    }

    fun onToastShown() {
        _toast.value = null
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
                return ArtistInfoViewModel((application as QuaverApplication).appContainer.artistRepository) as T
            }
        }
    }
}