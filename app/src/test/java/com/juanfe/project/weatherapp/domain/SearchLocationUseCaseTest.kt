package com.juanfe.project.weatherapp.domain

import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

class SearchLocationUseCaseTest{

    @Mock
    private lateinit var searchLocationRepository: SearchLocationRepository

    private lateinit var searchLocationUseCase: SearchLocationUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        searchLocationUseCase = SearchLocationUseCase(searchLocationRepository)
    }

    @Test
    fun `invoke should return search history`() = runTest {
        val rootForecast = listOf(mock(SearchModel::class.java))

        //Arrange
        Mockito.`when`(searchLocationRepository.searchLocation("Bogota")).thenReturn(Result.success(rootForecast))

        //Act
        val result = searchLocationUseCase.invoke("Bogota")

        //Assert
        assertTrue(result.isSuccess)

    }

}