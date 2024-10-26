package com.juanfe.project.weatherapp.ui.search

sealed class UserIntent() {
    data class SearchProduct(val query: String) : UserIntent()
    data object TapSearch : UserIntent()


}