package com.spotify.quavergd06.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spotify.quavergd06.data.model.Moment

class HomeViewModel : ViewModel() {

    private val _navigateToMomentDetail = MutableLiveData<Moment>(null)
    val navigateToMomentDetail: LiveData<Moment>
        get() = _navigateToMomentDetail
    fun onMomentClick(moment: Moment) {
        _navigateToMomentDetail.value = moment
    }

}