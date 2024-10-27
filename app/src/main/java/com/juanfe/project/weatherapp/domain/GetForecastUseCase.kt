package com.juanfe.project.weatherapp.domain

import javax.inject.Inject

class GetForecastUseCase @Inject constructor(private val searchLocationRepository: SearchLocationRepository) {
    suspend operator fun invoke(query: String) = searchLocationRepository.getForecast(query = query)

}


