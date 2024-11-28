package com.pvz.datadots.presentation.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvz.datadots.data.db.PointEntity
import com.pvz.datadots.domain.model.Point
import com.pvz.datadots.domain.repository.PointsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val repository: PointsRepository
) : ViewModel() {

    private val _pointList = MutableLiveData<List<Point>>()
    val pointList: LiveData<List<Point>> get() = _pointList

    fun getAllPoints(onResult: (List<PointEntity>) -> Unit) {
        viewModelScope.launch {
            val points = repository.getAllPoints()
            onResult(points)
        }
    }

    fun deleteAllPoints() {
        viewModelScope.launch {
            repository.deleteAllPoints()
        }
    }
}
