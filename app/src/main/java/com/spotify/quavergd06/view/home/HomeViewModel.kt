package com.spotify.quavergd06.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spotify.quavergd06.data.model.Moment
import com.spotify.quavergd06.data.model.StatsItem
import com.spotify.quavergd06.data.model.User

class HomeViewModel : ViewModel() {
    private val _user = MutableLiveData<User>(null)
    val user: LiveData<User>
        get() = _user

    var userInSession: User? = null
        set(value) {
            field = value
            _user.value = value!!
        }

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

    private val _navigateFromHistoryToTrackDetail = MutableLiveData<StatsItem>(null)
    val navigateFromHistoryToTrackDetail: LiveData<StatsItem>
        get() = _navigateFromHistoryToTrackDetail
    fun navigateFromHistoryToTrackDetail(statsItem: StatsItem) {
        _navigateFromHistoryToTrackDetail.value = statsItem
    }

    private val _navigateFromStatsToTrackDetail = MutableLiveData<StatsItem>(null)
    val navigateFromStatsToTrackDetail: LiveData<StatsItem>
        get() = _navigateFromStatsToTrackDetail
    fun navigateFromStatsToTrackDetail(statsItem: StatsItem) {
        _navigateFromStatsToTrackDetail.value = statsItem
    }
}