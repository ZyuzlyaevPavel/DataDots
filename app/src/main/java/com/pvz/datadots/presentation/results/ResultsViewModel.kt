package com.pvz.datadots.presentation.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pvz.datadots.domain.model.Point

class ResultsViewModel : ViewModel() {

    private val _pointList = MutableLiveData<List<Point>>()
    val pointList: LiveData<List<Point>> get() = _pointList

}
