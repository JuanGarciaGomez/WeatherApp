package com.juanfe.project.weatherapp.domain

import javax.inject.Inject

class SearchLocationUseCase @Inject constructor(private val searchLocationRepository: SearchLocationRepository) {
    suspend operator fun invoke(query: String) = searchLocationRepository.searchProduct(query = query)

}


