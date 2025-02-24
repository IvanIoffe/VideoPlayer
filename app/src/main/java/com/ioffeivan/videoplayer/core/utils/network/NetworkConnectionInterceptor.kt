package com.ioffeivan.videoplayer.core.utils.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkConnectionInterceptor @Inject constructor(
    private val context: Context,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isNetworkAvailable(context)) {
            throw NoNetworkException()
        }

        return chain.proceed(chain.request())
    }
}