package com.example.findpix.di

import com.example.findpix.BuildConfig
import com.example.findpix.data.source.remote.PixaBayDataSource
import com.example.findpix.data.source.remote.PixaBayDataSourceImpl
import com.example.findpix.data.source.remote.SearchApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL.toHttpUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authorizationInterceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authorizationInterceptor)
            .build()
    @Provides
    @Singleton
    fun provideAuthorizationInterceptor(): Interceptor = Interceptor {
        val url: HttpUrl = it.request().url
            .newBuilder()
            .addQueryParameter("key", BuildConfig.API_KEY)
            .build()
        val request: Request = it.request().newBuilder().url(url).build()
        it.proceed(request)
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
}