package com.ioffeivan.videoplayer.core.data.mapper

import com.ioffeivan.videoplayer.core.data.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T, R> Flow<Result<T>>.mapToDomainFlow(mapper: (T) -> R,): Flow<Result<R>> {
    return map { result ->
        when (result) {
            is Result.Success -> {
                Result.Success(mapper(result.data))
            }

            is Result.Error -> Result.Error(result.message)
            Result.Loading -> Result.Loading
        }
    }
}

fun <T, R> Flow<Result<T>>.mapToDomainFlow(
    mapper: (T) -> R,
    action: suspend (T) -> Unit,
): Flow<Result<R>> {
    return map { result ->
        when (result) {
            is Result.Success -> {
                action(result.data)
                Result.Success(mapper(result.data))
            }

            is Result.Error -> Result.Error(result.message)
            Result.Loading -> Result.Loading
        }
    }
}