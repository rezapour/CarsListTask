package me.rezapour.sixttask.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.rezapour.sixttask.data.network.networkmapper.CarDataMapper
import me.rezapour.sixttask.data.network.retrofit.ApiService
import me.rezapour.sixttask.model.Car
import me.rezapour.sixttask.utils.DataState

class CarRepositoryImpl constructor(
    private val api: ApiService,
    private val mapper: CarDataMapper
) : CarsRepository {

    override suspend fun getCars(): Flow<DataState<List<Car>>> = flow {
        try {
            val response = api.getCars()
            val result = if (response.isSuccessful) {
                val carList = response.body()
                if (carList != null && carList.isNotEmpty())
                    DataState.Success(mapper.listEntityToListDomain(carList))
                else
                    DataState.Error("Internet Error Problem")
            } else
                DataState.Error("Internet Error Problem")

            emit(result)
        } catch (e: Exception) {
            emit(DataState.Error(e.message.toString()))
        }
    }
}