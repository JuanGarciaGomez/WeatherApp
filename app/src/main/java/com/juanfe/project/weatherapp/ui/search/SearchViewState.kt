package com.juanfe.project.weatherapp.ui.search

import com.juanfe.project.weatherapp.domain.SearchModel

sealed class SearchViewState() {
    data class Loading(val firstOpen: Boolean = false) : SearchViewState()
    data class Error(val errorMsg: String) : SearchViewState()
    data class Success(val searchLocationModel: List<SearchModel>) : SearchViewState()

}