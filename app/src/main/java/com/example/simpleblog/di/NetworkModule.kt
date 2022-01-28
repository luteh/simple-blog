package com.example.simpleblog.di

import android.content.Context
import com.example.simpleblog.BuildConfig
import com.example.simpleblog.data.remote.apiservice.MyApiService
import com.example.simpleblog.common.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return with(OkHttpClient.Builder()) {
            addInterceptor(loggingInterceptor)
            addInterceptor(NetworkConnectionInterceptor(context))
            build()
        }
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return with(Retrofit.Builder()) {
            baseUrl(BuildConfig.API_URL)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
            build()
        }
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): MyApiService {
        return retrofit.create(MyApiService::class.java)
    }
}