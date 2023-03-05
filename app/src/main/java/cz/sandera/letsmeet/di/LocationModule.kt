package cz.sandera.letsmeet.di

import cz.sandera.letsmeet.data.location.LocationTrackerImpl
import cz.sandera.letsmeet.domain.location.LocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationTracker(defaultLocationTracker: LocationTrackerImpl): LocationTracker
}