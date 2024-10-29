package com.juanfe.project.weatherapp.ui.search

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import app.cash.turbine.test
import com.google.android.gms.location.FusedLocationProviderClient
import com.juanfe.project.weatherapp.R
import com.juanfe.project.weatherapp.domain.GetForecastUseCase
import com.juanfe.project.weatherapp.domain.RootForecastModel
import com.juanfe.project.weatherapp.domain.SearchLocationUseCase
import com.juanfe.project.weatherapp.domain.SearchModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.internal.concurrent.Task
import org.junit.Before
import android.Manifest
import junit.framework.TestCase.assertNotNull
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    @Mock
    private lateinit var getForecastUseCase: GetForecastUseCase

    @Mock
    private lateinit var searchLocationUseCase: SearchLocationUseCase

    @Mock
    private lateinit var context: Context

    private lateinit var viewModel: SearchViewModel

    // Set up the TestCoroutineDispatcher for coroutines testing
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)

        // Initialize the ViewModel with mocked dependencies
        viewModel = SearchViewModel(
            getForecastUseCase = getForecastUseCase,
            searchLocationUseCase = searchLocationUseCase,
            context = context
        )
    }

    @Test
    fun `search successful updates viewState to Success`() = runTest {

        val mockSearch = listOf(SearchModel(1, "Bogota", "Cundinamarca", "Colombia", 12.0, 12.0))

        `when`(searchLocationUseCase.invoke("Bogota")).thenReturn(Result.success(mockSearch))
        // Observe viewState
        viewModel.viewState.test {
            // Trigger search intent
            viewModel.handleIntent(UserIntent.SearchLocation("Bogota"))

            // Verify initial loading state
            assertEquals(SearchViewState.Loading, awaitItem())
            assertEquals(SearchViewState.SearchLocationSuccess(mockSearch), awaitItem())
            cancelAndIgnoreRemainingEvents()

        }
    }

    @Test
    fun `search successful updates viewState to Error`() = runTest {

        `when`(searchLocationUseCase.invoke("bog")).thenReturn(Result.failure(Exception()))
        `when`(context.getString(R.string.no_weather_location)).thenReturn("No hay Lugar para esta busqueda")
        // Observe viewState
        viewModel.viewState.test {
            // Trigger search intent
            viewModel.handleIntent(UserIntent.SearchLocation("Bogota"))

            // Verify initial loading state
            assertEquals(SearchViewState.Loading, awaitItem())
            assertTrue(awaitItem() is SearchViewState.Error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `get forecast successful updates viewState to success`() = runTest {

        val rootForecast = mock(RootForecastModel::class.java)

        `when`(getForecastUseCase.invoke("Bogota")).thenReturn(Result.success(rootForecast))
        `when`(context.getString(R.string.no_weather_location)).thenReturn("No hay Lugar para esta busqueda")
        // Observe viewState
        viewModel.viewState.test {
            // Trigger search intent
            viewModel.handleIntent(UserIntent.GetForecast("Bogota"))
            // Verify initial loading state
            assertEquals(SearchViewState.Loading, awaitItem())
            assertEquals(SearchViewState.ForecastSuccess(rootForecast), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }


}