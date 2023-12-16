package com.spotify.quavergd06.view.home.moments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spotify.quavergd06.QuaverApplication
import com.spotify.quavergd06.data.MomentsRepository
import com.spotify.quavergd06.data.model.Moment
import kotlinx.coroutines.launch

class MomentEditViewModel(private val repository: MomentsRepository) : ViewModel(){


    private val _bottomNavigationViewVisibility = MutableLiveData<Boolean>()
    val bottomNavigationViewVisibility: LiveData<Boolean>
        get() = _bottomNavigationViewVisibility

    // Function to update BottomNavigationView visibility
    fun setBottomNavigationViewVisibility(visible: Boolean) {
        _bottomNavigationViewVisibility.value = visible
    }

    private val _momentDetail = MutableLiveData<Moment>(null)
    val momentDetail: LiveData<Moment>
        get() = _momentDetail
    var moment : Moment? = null
        set(value) {
            field = value
            getMoment()
        }

    private fun getMoment() {
        if (moment != null) {
            viewModelScope.launch {
                val _moment = moment!!.momentId?.let { repository.fetchMomentDetail(it) }
                _momentDetail.value = _moment
            }
        }
    }

    fun insertMoment(moment: Moment) {
        viewModelScope.launch { repository.insertMoment(moment) }
    }


    companion object {
        val Factory : ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T { // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return MomentEditViewModel( (application as QuaverApplication).appContainer.momentsRepository, ) as T }
        }
    }
}