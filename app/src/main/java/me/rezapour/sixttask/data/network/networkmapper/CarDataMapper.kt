package me.rezapour.sixttask.data.network.networkmapper

import me.rezapour.sixttask.data.network.model.CarsNetworkEntity
import me.rezapour.sixttask.model.Car
import me.rezapour.sixttask.utils.Mapper
import javax.inject.Inject

class CarDataMapper @Inject constructor() : Mapper<CarsNetworkEntity, Car> {
    override fun entityToDomain(entity: CarsNetworkEntity): Car {
        return Car(
            id = entity.id,
            modelIdentifier = entity.modelIdentifier,
            modelName = entity.modelName,
            name = entity.name,
            make = entity.make,
            group = entity.group,
            color = entity.color,
            series = entity.series,
            fuelType = entity.fuelType,
            fuelLevel = entity.fuelLevel,
            transmission = entity.transmission,
            licensePlate = entity.licensePlate,
            latitude = entity.latitude,
            longitude = entity.longitude,
            innerCleanliness = entity.innerCleanliness,
            carImageUrl = entity.carImageUrl
        )
    }


    fun listEntityToListDomain(entityList: List<CarsNetworkEntity>): List<Car> {
        return entityList.map { entityList -> entityToDomain(entityList) }
    }
}