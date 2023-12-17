package com.spotify.quavergd06.view.home.stats.detailFragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.spotify.quavergd06.QuaverApplication
import com.spotify.quavergd06.api.APIError
import com.spotify.quavergd06.data.TracksRepository
import com.spotify.quavergd06.data.model.StatsItem
import com.spotify.quavergd06.data.model.User
import com.spotify.quavergd06.data.toStatsItem
import kotlinx.coroutines.launch

class TrackInfoViewModel (
    private val repository: TracksRepository
) : ViewModel() {

    private val _trackDetail = MutableLiveData<StatsItem>(null)
    val trackDetail: LiveData<StatsItem>
        get() = _trackDetail

    private val _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
        get() = _toast

    var user: User? = null
    var track: StatsItem? = null
        set(value) {
            field = value
            getTrack()
        }

    private fun getTrack() {
        if (track!=null)
            viewModelScope.launch{
                try{
                    val _track = repository.fetchTrackDetail(track?.id!!)
                    _trackDetail.value = _track.toStatsItem()
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
                return TrackInfoViewModel((application as QuaverApplication).appContainer.tracksRepository) as T
            }
        }
    }
}