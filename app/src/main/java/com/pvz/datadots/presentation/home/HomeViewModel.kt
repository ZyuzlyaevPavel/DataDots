package com.pvz.datadots.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvz.datadots.domain.repository.PointsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: PointsRepository
) : ViewModel() {

    private val _pointCount = MutableLiveData<String>()
    val pointCount: LiveData<String> get() = _pointCount

    private val _navigationEvent = MutableSharedFlow<Unit>()
    val navigationEvent: SharedFlow<Unit> get() = _navigationEvent.asSharedFlow()

    private val _errorEvent = MutableSharedFlow<PointFetchError>()
    val errorEvent: SharedFlow<PointFetchError> get() = _errorEvent.asSharedFlow()

    fun setPointCount(count: String) {
        _pointCount.value = count
    }

    fun fetchPoints(count: Int?) {
        viewModelScope.launch {
            if (count != null) {
                try {
                    val response = repository.fetchPoints(count)
                    if (response.isSuccessful) {
                        val points = response.body()?.points.orEmpty()
                        Log.d("main", "fetchPoints() is successful")
                        repository.insertPoints(points)
                        repository.getAllPoints()
                        _navigationEvent.emit(Unit)
                    } else {
                        Log.e("main", "fetchPoints() is unsuccessful error code ${response.code()}")
                        _errorEvent.emit(PointFetchError.RemoteError(response.code()))
                    }
                } catch (e: Exception) {
                    Log.e("main", "fetchPoints() exception", e)
                    _errorEvent.emit(PointFetchError.UnknownRemoteError)
                }
            } else {
                Log.e("main", "count is null")
                _errorEvent.emit(PointFetchError.ValueError)
            }
        }
    }
}
