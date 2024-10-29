package com.juanfe.project.weatherapp.data.repository.di

import com.juanfe.project.weatherapp.data.repository.SearchLocationRepositoryImpl
import com.juanfe.project.weatherapp.domain.SearchLocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Binds
    fun bindsSearchLocationRepository(searchLocationRepositoryImpl: SearchLocationRepositoryImpl): SearchLocationRepository


}