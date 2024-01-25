package com.example.findpix.di

import com.example.findpix.data.repository.SearchImageRepository
import com.example.findpix.data.repository.SearchImageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindSearchImageRepository(searchImageRepositoryImpl: SearchImageRepositoryImpl): SearchImageRepository
}