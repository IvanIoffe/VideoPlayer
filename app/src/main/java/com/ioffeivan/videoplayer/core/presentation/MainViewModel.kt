package com.ioffeivan.videoplayer.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ioffeivan.videoplayer.core.utils.network.ConnectionObserver
import com.ioffeivan.videoplayer.core.utils.network.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    connectionObserver: ConnectionObserver
) : ViewModel() {

    val networkState = connectionObserver.observe()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = NetworkState.INITIAL
        )
}