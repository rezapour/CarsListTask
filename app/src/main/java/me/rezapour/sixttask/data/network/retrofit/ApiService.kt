package me.rezapour.sixttask.data.network.retrofit


import me.rezapour.sixttask.data.network.model.CarsNetworkEntity
import retrofit2.Response

import retrofit2.http.GET

interface ApiService {

    @GET("cars")
    suspend fun getCars(): Response<List<CarsNetworkEntity>>

}