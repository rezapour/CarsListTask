package me.rezapour.sixttask.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import me.rezapour.mytask.util.MainCoroutineRule
import me.rezapour.sixttask.data.network.NetworkDataProvider
import me.rezapour.sixttask.model.Car
import me.rezapour.sixttask.utils.DataProviderException
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.whenever


class CarRepositoryImplTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var dataProvider: NetworkDataProvider

    lateinit var repository: CarsRepository

    @Before
    fun before() {
        dataProvider = mock()
        repository = CarRepositoryImpl(dataProvider)
    }

    @Test
    fun `get cars return cars when response is successful`() {
        runBlocking {
            whenever(dataProvider.getCars()).thenReturn(createDomainCars())

            repository.getCars().test {
                assertThat(awaitItem()).isEqualTo(createDomainCars())
                awaitComplete()
            }
            Mockito.verify(dataProvider, times(1)).getCars()
        }
    }

    @Test
    fun `get cars unsuccessful when response has error`() {
        runBlocking {
            whenever(dataProvider.getCars()).thenThrow(DataProviderException::class.java)

            repository.getCars().test {
                assertThat(awaitError()).isInstanceOf(DataProviderException::class.java)
            }
            Mockito.verify(dataProvider, times(1)).getCars()
        }
    }


    private fun createDomainCars(): List<Car> {
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
        return arrayListOf(car1, car2)
    }

}