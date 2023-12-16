package com.spotify.quavergd06.view.home.moments

import android.service.autofill.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.spotify.quavergd06.QuaverApplication
import com.spotify.quavergd06.data.MomentsRepository
import com.spotify.quavergd06.data.model.Moment

class MomentViewModel(
    repository: MomentsRepository
) : ViewModel() {

    private val _moments = repository.moments
    val moments: LiveData<List<Moment>> get() = _moments

    private val _filterQuery = MutableLiveData<String>()

    // Function to update filter query
    fun setFilterQuery(query: String) {
        _filterQuery.value = query
    }

    val filteredMoments: LiveData<List<Moment>> =
        Transformations.switchMap(_filterQuery) {
            _filterQuery.value?.let {
            repository.getFilteredMoments(
                it
            )
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
                return MomentViewModel( (application as QuaverApplication).appContainer.momentsRepository, ) as T }
        }
    }
}