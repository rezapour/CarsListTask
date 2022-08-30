package me.rezapour.sixttask.data.network.networkmapper

import com.google.common.truth.Truth.assertThat
import me.rezapour.sixttask.data.network.model.CarsNetworkEntity
import me.rezapour.sixttask.model.Car
import org.junit.Before
import org.junit.Test


internal class CarDataMapperTest {

    lateinit var carDataMapper: CarDataMapper

    @Before
    fun init() {
        carDataMapper = CarDataMapper()
    }

    @Test
    fun `test mapper class`() {
        assertThat(carDataMapper.listEntityToListDomain(createSampleCarEntity())).isEqualTo(
            createDomainCars()
        )
    }


    private fun createSampleCarEntity(): List<CarsNetworkEntity> {
        val networkEntityCar1 = CarsNetworkEntity(
            id = "WMWSW31030T222518",
            modelIdentifier = "mini",
            modelName = "MINI",
            name = "Vanessa",
            make = "BMW",
            group = "MINI",
            color = "midnight_black",
            series = "MINI",
            fuelType = "D",
            fuelLevel = 0.7,
            transmission = "M",
            licensePlate = "M-VO0259",
            latitude = 48.134557,
            longitude = 11.576921,
            innerCleanliness = "REGULAR",
            carImageUrl = "https://cdn.sixt.io/codingtask/images/mini.png"
        )

        val networkEntityCar2 = CarsNetworkEntity(
            id = "WMWSW31030",
            modelIdentifier = "bmw_1er",
            modelName = "BMW 1er",
            name = "Lasse",
            make = "BMW",
            group = "BMW",
            color = "saphirschwarz",
            series = "1er",
            fuelType = "P",
            fuelLevel = 0.35,
            transmission = "A",
            licensePlate = "M-333111",
            latitude = 48.134557,
            longitude = 11.576921,
            innerCleanliness = "REGULAR",
            carImageUrl = "https://cdn.sixt.io/codingtask/images/mini.png"
        )
        return arrayListOf(networkEntityCar1, networkEntityCar2)
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