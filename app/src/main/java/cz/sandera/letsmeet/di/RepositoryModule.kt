package cz.sandera.letsmeet.di

import cz.sandera.letsmeet.data.repository.WeatherPreferencesRepositoryImpl
import cz.sandera.letsmeet.data.repository.WeatherRepositoryImpl
import cz.sandera.letsmeet.domain.repository.WeatherPreferencesRepository
import cz.sandera.letsmeet.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindWeatherPreferences(
        weatherPreferenceIml: WeatherPreferencesRepositoryImpl
    ): WeatherPreferencesRepository
}