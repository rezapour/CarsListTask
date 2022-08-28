package me.rezapour.sixttask.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.rezapour.sixttask.data.network.NetworkDataProvider
import me.rezapour.sixttask.model.Car

class CarRepositoryImpl constructor(
    private val dataProvider: NetworkDataProvider,
) : CarsRepository {
    override suspend fun getCars(): Flow<List<Car>> = flow {
        val data = dataProvider.getCars()
        emit(data)
    }
}