package me.rezapour.sixttask.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.rezapour.sixttask.data.network.NetworkDataProvider
import me.rezapour.sixttask.data.network.impl.NetworkDataProviderImpl
import me.rezapour.sixttask.data.network.networkmapper.CarDataMapper
import me.rezapour.sixttask.data.network.retrofit.ApiService
import me.rezapour.sixttask.data.repository.CarRepositoryImpl
import me.rezapour.sixttask.data.repository.CarsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        dataProvider: NetworkDataProvider,
    ): CarsRepository {
        return CarRepositoryImpl(dataProvider)
    }


    @Singleton
    @Provides
    fun provideDataNetworkProvider(
        api: ApiService,
        mapper: CarDataMapper
    ): NetworkDataProvider {
        return NetworkDataProviderImpl(api, mapper)
    }


}