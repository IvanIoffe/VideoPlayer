package com.ioffeivan.videoplayer.core.data.source.remote.di

import android.content.Context
import com.ioffeivan.videoplayer.core.utils.network.ConnectionObserver
import com.ioffeivan.videoplayer.core.utils.network.NetworkConnectionInterceptor
import com.ioffeivan.videoplayer.core.utils.network.NetworkConnectionObserver
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModuleProvider {

    @Singleton
    @Provides
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit.Builder {
        val baseUrl = "http://91.203.180.183:3000/"
        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(networkConnectionInterceptor: NetworkConnectionInterceptor): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            .addInterceptor(networkConnectionInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideNetworkConnectionInterceptor(
        @ApplicationContext context: Context
    ) = NetworkConnectionInterceptor(context)

    @Singleton
    @Provides
    fun provideNetworkConnectionObserver(
        @ApplicationContext context: Context
    ) = NetworkConnectionObserver(context)
}

@InstallIn(SingletonComponent::class)
@Module
interface NetworkModuleBinder {

    @Binds
    fun bindNetworkConnectionObserver(
        networkConnectionObserver: NetworkConnectionObserver
    ): ConnectionObserver
}