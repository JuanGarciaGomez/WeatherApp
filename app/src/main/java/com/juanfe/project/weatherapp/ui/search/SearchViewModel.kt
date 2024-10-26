package com.juanfe.project.weatherapp.ui.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanfe.project.weatherapp.R
import com.juanfe.project.weatherapp.domain.ExceptionService
import com.juanfe.project.weatherapp.domain.SearchLocationUseCase
import com.juanfe.project.weatherapp.domain.SearchModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchLocationUseCase: SearchLocationUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {


    private val _searchHistory = MutableStateFlow<List<String>?>(null)
    val searchHistory: StateFlow<List<String>?> get() = _searchHistory

    private val _viewState =
        MutableStateFlow<SearchViewState>(SearchViewState.Loading(firstOpen = true))
    val viewState: StateFlow<SearchViewState> = _viewState


    fun handleIntent(intent: UserIntent) {
        when (intent) {
            is UserIntent.SearchProduct -> search(intent.query)
            UserIntent.TapSearch -> {}
        }
    }

    private fun search(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.value = SearchViewState.Loading()
            val result = searchLocationUseCase.invoke(query)
            result.fold(onSuccess = { searchProduct ->
                handleSuccess(searchProduct)
            }, onFailure = {
                handleError(it)
            })
        }
    }


    private fun handleError(error: Throwable) {
        val exception = error as ExceptionService
        error.printStackTrace()
        _viewState.value = SearchViewState.Error(errorMsg = exception.msgError)
    }

    private fun handleSuccess(searchProduct: List<SearchModel>) {
        val productList = searchProduct
        if (productList.isEmpty()) _viewState.value =
            SearchViewState.Error(context.getString(R.string.no_weather_location))
        else _viewState.value = SearchViewState.Success(productList)
    }


}