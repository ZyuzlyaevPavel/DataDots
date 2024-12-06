package com.pvz.datadots.presentation.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvz.datadots.domain.model.Point
import com.pvz.datadots.domain.repository.PointsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val repository: PointsRepository
) : ViewModel() {

    private val _pointList = MutableLiveData<List<Point>>()
    val pointList: LiveData<List<Point>> get() = _pointList
    private val _spinnerId: MutableStateFlow<Int> = MutableStateFlow(0)
    val spinnerId: StateFlow<Int> get() = _spinnerId.asStateFlow()

    fun fetchPointsData() {
        viewModelScope.launch {
            val points = repository.getAllPoints()
            _pointList.postValue(points)
        }
    }

    fun deleteAllPoints() {
        viewModelScope.launch {
            repository.deleteAllPoints()
        }
    }

    fun updateSpinnerItemId(position: Int) {
        viewModelScope.launch {
            _spinnerId.emit(position)
        }
    }
}
