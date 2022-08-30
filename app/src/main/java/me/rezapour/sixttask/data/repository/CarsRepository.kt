package me.rezapour.sixttask.data.repository

import kotlinx.coroutines.flow.Flow
import me.rezapour.sixttask.utils.DataProviderException
import me.rezapour.sixttask.model.Car

interface CarsRepository {

    @Throws(DataProviderException::class)
    suspend fun getCars(): Flow<List<Car>>
}