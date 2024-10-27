package com.juanfe.project.weatherapp.ui.search

sealed class UserIntent() {
    data class SearchLocation(val query: String) : UserIntent()
    data class GetForecast(val query: String) : UserIntent()
    data object TapSearch : UserIntent()


}