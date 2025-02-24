package com.ioffeivan.videoplayer.core.utils.network

import kotlinx.coroutines.flow.Flow

interface ConnectionObserver {

    fun observe(): Flow<NetworkState>
}