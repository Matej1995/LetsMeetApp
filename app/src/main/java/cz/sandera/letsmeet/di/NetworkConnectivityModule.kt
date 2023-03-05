package cz.sandera.letsmeet.di

import cz.sandera.letsmeet.data.connectivity.NetworkConnectivityObserver
import cz.sandera.letsmeet.domain.util.ConnectivityObserver
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkConnectivityModule {

    @Binds
    @Singleton
    abstract fun bindNetworkConnectivity(connectivityObserver: NetworkConnectivityObserver): ConnectivityObserver
}