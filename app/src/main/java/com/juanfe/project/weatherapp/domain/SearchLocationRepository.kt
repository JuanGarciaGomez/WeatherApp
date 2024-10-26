package com.juanfe.project.weatherapp.domain

interface SearchLocationRepository {

    suspend fun searchProduct(query: String): Result<List<SearchModel>>


}