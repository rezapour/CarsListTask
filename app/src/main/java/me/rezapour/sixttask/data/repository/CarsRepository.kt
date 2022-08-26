package me.rezapour.sixttask.data.repository

import kotlinx.coroutines.flow.Flow
import me.rezapour.sixttask.model.Car
import me.rezapour.sixttask.utils.DataState

interface CarsRepository {

    suspend fun getCars(): Flow<DataState<List<Car>>>

}