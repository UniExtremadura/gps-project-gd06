package com.spotify.quavergd06.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spotify.quavergd06.data.model.Moment

class HomeViewModel : ViewModel() {

    private val _navigateFromMomentToDetail = MutableLiveData<Moment>(null)
    val navigateFromMomentToDetail: LiveData<Moment>
        get() = _navigateFromMomentToDetail
    fun navigateFromMomentToDetail(moment: Moment) {
        _navigateFromMomentToDetail.value = moment
    }

    private val _navigateFromMomentToMap = MutableLiveData<Boolean>(true)
    val navigateToMapFromMoment: LiveData<Boolean>
        get() = _navigateFromMomentToMap
    fun navigateFromMomentToMap(trigger: Boolean) {
        _navigateFromMomentToMap.value = trigger
    }

    private val _navigateFromDetailToEdit = MutableLiveData<Moment>(null)
    val navigateToMomentEditFromDetail: LiveData<Moment>
        get() = _navigateFromDetailToEdit
    fun navigateToMomentEditFromDetail(moment: Moment) {
        _navigateFromDetailToEdit.value = moment
    }

    private val _navigateFromEditToMoment = MutableLiveData<Boolean>(true)
    val navigateFromEditToMoment: LiveData<Boolean>
        get() = _navigateFromEditToMoment
    fun navigateFromEditToMoment(trigger: Boolean) {
        _navigateFromEditToMoment.value = trigger
    }

    private val _navigateFromMapToMoment = MutableLiveData<Boolean>(true)
    val navigateFromMapToMoment: LiveData<Boolean>
        get() = _navigateFromMapToMoment
    fun navigateFromMapToMoment(trigger: Boolean) {
        _navigateFromMapToMoment.value = trigger
    }

    private val _navigateFromMapToDetail = MutableLiveData<Moment>(null)
    val navigateFromMapToDetail: LiveData<Moment>
        get() = _navigateFromMapToDetail
    fun navigateFromMapToDetail(moment: Moment) {
        _navigateFromMapToDetail.value = moment
    }

}