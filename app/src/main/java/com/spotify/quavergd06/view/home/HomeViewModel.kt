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

    private val _navigateToMapFromMoment = MutableLiveData<Boolean>(true)
    val navigateToMapFromMoment: LiveData<Boolean>
        get() = _navigateToMapFromMoment
    fun navigateToMapFromMoment(trigger: Boolean) {
        _navigateToMapFromMoment.value = trigger
    }

    private val _navigateToMomentEditFromDetail = MutableLiveData<Moment>(null)
    val navigateToMomentEditFromDetail: LiveData<Moment>
        get() = _navigateToMomentEditFromDetail
    fun navigateToMomentEditFromDetail(moment: Moment) {
        _navigateToMomentEditFromDetail.value = moment
    }

    private val _navigateToMomentFromEdit = MutableLiveData<Boolean>(true)
    val navigateToMomentFromEdit: LiveData<Boolean>
        get() = _navigateToMomentFromEdit
    fun navigateToMomentFromEdit(trigger: Boolean) {
        _navigateToMomentFromEdit.value = trigger
    }

    private val _navigateToMomentFromMap = MutableLiveData<Boolean>(true)
    val navigateToMomentFromMap: LiveData<Boolean>
        get() = _navigateToMomentFromMap
    fun navigateToMomentFromMap(trigger: Boolean) {
        _navigateToMomentFromMap.value = trigger
    }

    private val _navigateToDetailFromMap = MutableLiveData<Moment>(null)
    val navigateToDetailFromMap: LiveData<Moment>
        get() = _navigateToDetailFromMap
    fun navigateToDetailFromMap(moment: Moment) {
        _navigateToDetailFromMap.value = moment
    }

}