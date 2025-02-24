package com.ioffeivan.videoplayer.core.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorRemote(

    @SerialName("error")
    val message: String,
)