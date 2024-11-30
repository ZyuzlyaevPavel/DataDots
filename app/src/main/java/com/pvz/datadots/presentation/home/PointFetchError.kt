package com.pvz.datadots.presentation.home

sealed class PointFetchError {
    data class RemoteError(val responseCode: Int) : PointFetchError()
    data object UnknownRemoteError : PointFetchError()
    data object ValueError : PointFetchError()
}