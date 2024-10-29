package com.juanfe.project.weatherapp.ui.search

import com.juanfe.project.weatherapp.domain.RootForecastModel
import com.juanfe.project.weatherapp.domain.SearchModel

sealed class SearchViewState() {
    data object Loading : SearchViewState()
    data class Error(val errorMsg: String) : SearchViewState()
    data class SearchLocationSuccess(val searchLocationModel: List<SearchModel>) : SearchViewState()
    data class ForecastSuccess(val getForecast: RootForecastModel) : SearchViewState()

}