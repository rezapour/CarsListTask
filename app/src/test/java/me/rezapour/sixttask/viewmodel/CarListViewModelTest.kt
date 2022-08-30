package me.rezapour.sixttask.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import me.rezapour.mytask.util.MainCoroutineRule
import me.rezapour.sixttask.data.repository.CarsRepository
import me.rezapour.sixttask.model.Car
import me.rezapour.sixttask.utils.DataProviderException
import me.rezapour.sixttask.utils.DataState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.IOException
import java.lang.RuntimeException


internal class CarListViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var viewModel: CarListViewModel
    lateinit var repository: CarsRepository

    @Before
    fun setUp() {
        repository = mock()
        viewModel = CarListViewModel(repository)
    }

    @Test
    fun `loadData return Earliest when response is successful`() {
        runBlocking() {
            whenever(repository.getCars()).thenReturn(createDomainCars())
        }
        viewModel.loadData()

        val valueRespond = viewModel.carDataState.getOrAwaitValueTest()
        assertThat(valueRespond).isInstanceOf(DataState.Success::class.java)
    }

    @Test
    fun `loadData return Error when response is DataProviderException`() {
        runBlocking() {
            whenever(repository.getCars()).thenThrow(DataProviderException::class.java)
        }
        viewModel.loadData()
        val valueRespond = viewModel.carDataState.getOrAwaitValueTest()
        assertThat(valueRespond).isInstanceOf(DataState.Error::class.java)
    }

    @Test
    fun `loadData return Error when response is Exception`() {
        runBlocking() {
            whenever(repository.getCars()).thenThrow(RuntimeException::class.java)
        }
        viewModel.loadData()
        val valueRespond = viewModel.carDataState.getOrAwaitValueTest()
        assertThat(valueRespond).isInstanceOf(DataState.DefaultError::class.java)
    }


    private fun createDomainCars(): Flow<List<Car>> = flow {
        val car1 = Car(
            id = "WMWSW31030T222518",
            modelIdentifier = "mini",
            modelName = "MINI",
            name = "Vanessa",
            make = "BMW",
            group = "MINI",
            color = "Midnight black",
            series = "MINI",
            fuelType = "Diesel",
            fuelLevel = "70",
            transmission = "Manual",
            licensePlate = "M-VO0259",
            latitude = 48.134557,
            longitude = 11.576921,
            innerCleanliness = "REGULAR",
            carImageUrl = "https://cdn.sixt.io/codingtask/images/mini.png"
        )

        val car2 = Car(
            id = "WMWSW31030",
            modelIdentifier = "bmw_1er",
            modelName = "BMW 1er",
            name = "Lasse",
            make = "BMW",
            group = "BMW",
            color = "Saphirschwarz",
            series = "1er",
            fuelType = "Petrol",
            fuelLevel = "35",
            transmission = "Automatic",
            licensePlate = "M-333111",
            latitude = 48.134557,
            longitude = 11.576921,
            innerCleanliness = "REGULAR",
            carImageUrl = "https://cdn.sixt.io/codingtask/images/mini.png"
        )
        emit(arrayListOf(car1, car2))
    }
}