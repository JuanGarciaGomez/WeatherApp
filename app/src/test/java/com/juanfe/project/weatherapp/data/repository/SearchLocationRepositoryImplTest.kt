package com.juanfe.project.weatherapp.data.repository

import android.content.Context
import com.juanfe.project.weatherapp.R
import com.juanfe.project.weatherapp.data.network.WeatherService
import com.juanfe.project.weatherapp.data.network.response.AstroResponse
import com.juanfe.project.weatherapp.data.network.response.Condition2Response
import com.juanfe.project.weatherapp.data.network.response.ConditionResponse
import com.juanfe.project.weatherapp.data.network.response.CurrentResponse
import com.juanfe.project.weatherapp.data.network.response.DayResponse
import com.juanfe.project.weatherapp.data.network.response.ForecastDayResponse
import com.juanfe.project.weatherapp.data.network.response.ForecastResponse
import com.juanfe.project.weatherapp.data.network.response.LocationResponse
import com.juanfe.project.weatherapp.data.network.response.RootForecastResponse
import com.juanfe.project.weatherapp.data.network.response.SearchResponse
import com.juanfe.project.weatherapp.domain.ExceptionService
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response
import toDomain

class SearchLocationRepositoryImplTest {

    @Mock
    private lateinit var weatherService: WeatherService

    @Mock
    private lateinit var context: Context

    private lateinit var searchLocationRepositoryImpl: SearchLocationRepositoryImpl


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Mockito.`when`(context.getString(R.string.unknown_error)).thenReturn("Error inesperado")
        searchLocationRepositoryImpl = SearchLocationRepositoryImpl(weatherService, context)
    }

    @Test
    fun `test search location success`() = runTest {
        val query = "example"
        val response = Response.success(
            listOf(
                SearchResponse(
                    1,
                    "Bogota",
                    "Cundinamarca",
                    "Colombia",
                    12.0,
                    12.0
                )
            )
        )

        Mockito.`when`(weatherService.searchLocation(query)).thenReturn(response)


        val result = searchLocationRepositoryImpl.searchLocation(query)

        TestCase.assertTrue(result.isSuccess)
        assertEquals(response.body()?.map { it.toDomain() }, result.getOrNull())
    }

    @Test
    fun `test search location error`() = runTest {

        Mockito.`when`(weatherService.searchLocation("Bogota"))
            .thenThrow(RuntimeException("Error inesperado"))

        val result = searchLocationRepositoryImpl.searchLocation("Bogota")

        TestCase.assertTrue(result.isFailure)
        TestCase.assertEquals(
            "Error inesperado",
            (result.exceptionOrNull() as ExceptionService).msgError
        )
    }

    @Test
    fun `test search location success code != 200`() = runTest {
        val query = "Bogota"
        val response = Response.success(
            250,
            listOf(
                SearchResponse(
                    1,
                    "Bogota",
                    "Cundinamarca",
                    "Colombia",
                    12.0,
                    12.0
                )
            )
        )

        Mockito.`when`(weatherService.searchLocation(query)).thenReturn(response)


        val result = searchLocationRepositoryImpl.searchLocation(query)

        TestCase.assertTrue(result.isFailure)
        TestCase.assertEquals("Error inesperado", (result.exceptionOrNull() as Exception).message)
    }


    @Test
    fun `test get forecast success`() = runTest {
        val query = "Bogota"
        val mockResponseBody = mockRootForecastResponse()
        val response = Response.success(mockResponseBody)

        Mockito.`when`(weatherService.getForecast(query)).thenReturn(response)

        val result = searchLocationRepositoryImpl.getForecast(query)

        TestCase.assertTrue(result.isSuccess)
    }

    private fun mockRootForecastResponse(): RootForecastResponse {
        return RootForecastResponse(
            location = LocationResponse(
                name = "Bogota",
                region = "Cundinamarca",
                country = "Colombia",
                lat = 4.611,
                lon = -74.08175,
                tzId = "America/Bogota",
                localtimeEpoch = 1636121234.0,
                localtime = "2021-11-04 10:00"
            ),
            current = CurrentResponse(
                lastUpdatedEpoch = 1636121234.0,
                lastUpdated = "2021-11-04 10:00",
                tempC = 18.0,
                tempF = 64.4,
                isDay = 1.0,
                condition = ConditionResponse(
                    text = "Sunny",
                    icon = "//cdn.weatherapi.com/weather/64x64/day/113.png",
                    code = 1000.0
                ),
                windMph = 5.0,
                windKph = 8.0,
                windDegree = 270.0,
                windDir = "W",
                pressureMb = 1012.0,
                pressureIn = 30.4,
                precipMm = 0.0,
                precipIn = 0.0,
                humidity = 50.0,
                cloud = 0.0,
                feelsLikeC = 18.0,
                feelsLikeF = 64.4,
                windchillC = 18.0,
                windchillF = 64.4,
                heatIndexC = 18.0,
                heatIndexF = 64.4,
                dewPointC = 10.0,
                dewPointF = 50.0,
                visKm = 10.0,
                visMiles = 6.0,
                uv = 5.0,
                gustMph = 6.0,
                gustKph = 9.7
            ),
            forecast = ForecastResponse(
                forecastDay = listOf(
                    ForecastDayResponse(
                        date = "2021-11-04",
                        dateEpoch = 1636121234.0,
                        day = DayResponse(
                            maxTempC = 22.0,
                            maxTempF = 71.6,
                            minTempC = 14.0,
                            minTempF = 57.2,
                            avgTempC = 18.0,
                            avgTempF = 64.4,
                            maxWindMph = 7.0,
                            maxWindKph = 11.3,
                            totalPrecipMm = 0.0,
                            totalPrecipIn = 0.0,
                            totalSnowCm = 0.0,
                            avgVisKm = 10.0,
                            avgVisMiles = 6.0,
                            avgHumidity = 50.0,
                            dailyWillItRain = 0.0,
                            dailyChanceOfRain = 0.0,
                            dailyWillItSnow = 0.0,
                            dailyChanceOfSnow = 0.0,
                            condition = Condition2Response(
                                text = "Sunny",
                                icon = "//cdn.weatherapi.com/weather/64x64/day/113.png",
                                code = 1000.0
                            ),
                            uv = 5.0
                        ),
                        astro = AstroResponse(
                            sunrise = "06:00",
                            sunset = "18:00",
                            moonrise = "20:00",
                            moonSet = "06:00",
                            moonPhase = "Waning Crescent",
                            moonIllumination = 70.0,
                            isMoonUp = 1.0,
                            isSunUp = 1.0
                        ),
                        hour = listOf()
                    )
                )
            )
        )
    }


}