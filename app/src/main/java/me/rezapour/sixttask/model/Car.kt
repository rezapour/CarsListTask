package me.rezapour.sixttask.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Car(
    var id: String,
    var modelIdentifier: String,
    var modelName: String,
    var name: String,
    var make: String,
    var group: String,
    var color: String,
    var series: String,
    var fuelType: String,
    var fuelLevel: String,
    var transmission: String,
    var licensePlate: String,
    var latitude: Double,
    var longitude: Double,
    var innerCleanliness: String,
    var carImageUrl: String,
)