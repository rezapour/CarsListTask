package me.rezapour.sixttask.data.network

import me.rezapour.sixttask.utils.DataProviderException
import me.rezapour.sixttask.model.Car

interface NetworkDataProvider {
    @Throws(DataProviderException::class)
    suspend fun getCars(): List<Car>
}