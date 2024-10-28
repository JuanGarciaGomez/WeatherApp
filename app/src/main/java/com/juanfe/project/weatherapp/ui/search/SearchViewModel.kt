package com.juanfe.project.weatherapp.ui.search

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.juanfe.project.weatherapp.R
import com.juanfe.project.weatherapp.domain.ExceptionService
import com.juanfe.project.weatherapp.domain.GetForecastUseCase
import com.juanfe.project.weatherapp.domain.RootForecastModel
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
    private val getForecastUseCase: GetForecastUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _viewState =
        MutableStateFlow<SearchViewState>(SearchViewState.Loading(firstOpen = true))
    val viewState: StateFlow<SearchViewState> = _viewState

    fun handleIntent(intent: UserIntent) {
        when (intent) {
            is UserIntent.SearchLocation -> search(intent.query)
            UserIntent.TapSearch -> {}
            is UserIntent.GetForecast -> getForeCast(intent.query)
        }
    }

    private fun getForeCast(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.value = SearchViewState.Loading()
            val result = getForecastUseCase.invoke(query)
            result.fold(onSuccess = { forecast ->
                handleSuccess(searchProduct = null, forecast = forecast)
            }, onFailure = {
                handleError(it)
            })
        }
    }

    private fun search(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.value = SearchViewState.Loading()
            val result = searchLocationUseCase.invoke(query)
            result.fold(onSuccess = { searchProduct ->
                handleSuccess(searchProduct = searchProduct, forecast = null)
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

    private fun handleSuccess(
        searchProduct: List<SearchModel>?,
        forecast: RootForecastModel?
    ) {
        if (searchProduct != null) {
            if (searchProduct.isEmpty()) _viewState.value =
                SearchViewState.Error(context.getString(R.string.no_weather_location))
            else _viewState.value = SearchViewState.SearchLocationSuccess(searchProduct)
        } else {
            if (forecast != null) {
                _viewState.value = SearchViewState.ForecastSuccess(forecast)
            } else {
                _viewState.value =
                    SearchViewState.Error(context.getString(R.string.no_weather_location))
            }
        }
    }
}