package com.pvz.datadots.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _pointCount = MutableLiveData<Int>()
    val pointCount: LiveData<Int> get() = _pointCount

    fun setPointCount(count: Int) {
        _pointCount.value = count
    }
}
