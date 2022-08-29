package me.rezapour.sixttask.data.network.networkmapper

import me.rezapour.sixttask.R
import me.rezapour.sixttask.data.network.model.CarsNetworkEntity
import me.rezapour.sixttask.model.Car
import javax.inject.Inject

class CarDataMapper @Inject constructor() {
    private fun entityToDomain(entity: CarsNetworkEntity): Car {
        return Car(
            id = entity.id,
            modelIdentifier = entity.modelIdentifier,
            modelName = entity.modelName,
            name = entity.name,
            make = entity.make,
            group = entity.group,
            color = mapColor(entity.color),
            series = entity.series,
            fuelType = mapFuelType(entity.fuelType),
            fuelLevel = mapFuelLevel(entity.fuelLevel),
            transmission = mapTransmissionType(entity.transmission),
            licensePlate = entity.licensePlate,
            latitude = entity.latitude,
            longitude = entity.longitude,
            innerCleanliness = entity.innerCleanliness,
            carImageUrl = entity.carImageUrl
        )
    }


    fun listEntityToListDomain(entityList: List<CarsNetworkEntity>): List<Car> {
        return entityList.map { entity -> entityToDomain(entity) }
    }


    private fun mapTransmissionType(transmissionType: String) =
        if (transmissionType == "M") "Manual" else "Automatic"

    private fun mapFuelType(fuelType: String) = if (fuelType == "D") "Diesel" else "Electric"

    private fun mapColor(color: String) = color.replace("_", " ").capitalize()

    private fun mapFuelLevel(fuelLevel: Double): String = String.format("%.0f", (fuelLevel * 100))


}