package com.juanfe.project.weatherapp.domain

interface SearchLocationRepository {

    suspend fun searchLocation(query: String): Result<List<SearchModel>>
    suspend fun getForecast(query: String): Result<RootForecastModel>


}