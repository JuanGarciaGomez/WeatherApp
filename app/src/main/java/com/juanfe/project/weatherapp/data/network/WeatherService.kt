package com.juanfe.project.weatherapp.data.network

import com.juanfe.project.weatherapp.data.network.response.RootForecastResponse
import com.juanfe.project.weatherapp.data.network.response.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("search.json")
    suspend fun searchLocation(
        @Query("q") query: String
    ): Response<List<SearchResponse>>

    @GET("forecast.json")
    suspend fun getForecast(
        @Query("q") location: String,
        @Query("days") days: Int
    ): Response<RootForecastResponse>

}