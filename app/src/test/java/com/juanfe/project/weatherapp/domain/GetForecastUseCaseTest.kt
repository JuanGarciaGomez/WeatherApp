package com.juanfe.project.weatherapp.domain

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class GetForecastUseCaseTest{

    @Mock
    private lateinit var searchLocationRepository: SearchLocationRepository

    private lateinit var getForecastUseCase: GetForecastUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getForecastUseCase = GetForecastUseCase(searchLocationRepository)
    }

    @Test
    fun `invoke should return search history`() = runTest {
        val rootForecast = mock(RootForecastModel::class.java)


        //Arrange
        `when`(searchLocationRepository.getForecast("Bogota")).thenReturn(Result.success(rootForecast))

        //Act
        val result = getForecastUseCase.invoke("Bogota")

        //Assert
        assertTrue(result.isSuccess)

    }


}