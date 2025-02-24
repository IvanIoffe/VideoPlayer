package com.ioffeivan.videoplayer.core.data

import com.ioffeivan.videoplayer.core.data.source.remote.model.ErrorRemote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.json.Json
import retrofit2.Response

private const val TIMEOUT_MILLIS = 30000L

fun <T> apiRemoteRequestFlow(call: suspend () -> Response<T>): Flow<Result<T>> = flow {
    emit(Result.Loading)

    withTimeout(TIMEOUT_MILLIS) {
        try {
            val response = call()
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    emit(Result.Success(data))
                }
            } else {
                response.errorBody()?.let { error ->
                    val json = Json { ignoreUnknownKeys = true }
                    val errorRemote = json.decodeFromString<ErrorRemote>(error.string())
                    emit(Result.Error(errorRemote.message))
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: e.toString()))
        }
    } ?: emit(Result.Error("Ошибка загрузки. Попробуйте снова"))
}.flowOn(Dispatchers.IO)

fun <T> localRequestFlow(call: suspend () -> T): Flow<Result<T>> = flow {
    emit(Result.Loading)

    try {
        val response = call()
        emit(Result.Success(response))
    } catch (e: Exception) {
        emit(Result.Error(e.message ?: e.toString()))
    }
}.flowOn(Dispatchers.IO)