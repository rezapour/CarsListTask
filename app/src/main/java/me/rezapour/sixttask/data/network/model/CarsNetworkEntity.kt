package me.rezapour.sixttask.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CarsNetworkEntity(
    @Expose @SerializedName("id") var id: String,
    @Expose @SerializedName("modelIdentifier") var modelIdentifier: String,
    @Expose @SerializedName("modelName") var modelName: String,
    @Expose @SerializedName("name") var name: String,
    @Expose @SerializedName("make") var make: String,
    @Expose @SerializedName("group") var group: String,
    @Expose @SerializedName("color") var color: String,
    @Expose @SerializedName("series") var series: String,
    @Expose @SerializedName("fuelType") var fuelType: String,
    @Expose @SerializedName("fuelLevel") var fuelLevel: Double,
    @Expose @SerializedName("transmission") var transmission: String,
    @Expose @SerializedName("licensePlate") var licensePlate: String,
    @Expose @SerializedName("latitude") var latitude: Double,
    @Expose @SerializedName("longitude") var longitude: Double,
    @Expose @SerializedName("innerCleanliness") var innerCleanliness: String,
    @Expose @SerializedName("carImageUrl") var carImageUrl: String
)