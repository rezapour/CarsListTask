package me.rezapour.sixttask.data.network.impl

import me.rezapour.sixttask.utils.DataProviderException
import me.rezapour.sixttask.data.exception.ExceptionMapper
import me.rezapour.sixttask.data.network.NetworkDataProvider
import me.rezapour.sixttask.data.network.networkmapper.CarDataMapper
import me.rezapour.sixttask.data.network.retrofit.ApiService
import me.rezapour.sixttask.model.Car
import retrofit2.Response

class NetworkDataProviderImpl(private val api: ApiService, private val mapper: CarDataMapper) :
    NetworkDataProvider {

    override suspend fun getCars(): List<Car> {
        try {
            val response = api.getCars()
            if (response.isSuccessful) {
                if (response.isResponseValid())
                    return mapper.listEntityToListDomain(response.body()!!)
                else
                    throw DataProviderException(ExceptionMapper.toServerError())
            } else
                throw DataProviderException(ExceptionMapper.toApiCallErrorMessage(response.code()))

        } catch (e: Exception) {
            throw DataProviderException(ExceptionMapper.toInternetConnectionError())
        }
    }

    private fun <T> Response<T>.isResponseValid(): Boolean {
        return body() != null
    }
}