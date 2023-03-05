package cz.sandera.letsmeet.di

import cz.sandera.letsmeet.domain.use_case.FilterOutDigits
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object WeatherDomainModule {

    @Provides
    @ViewModelScoped
    fun provideFilterOutDigits(): FilterOutDigits {
        return FilterOutDigits()
    }
}
