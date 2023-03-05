package cz.sandera.letsmeet.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import cz.sandera.letsmeet.data.dataStore.WeatherPreferences
import cz.sandera.letsmeet.data.dataStore.serializer.WeatherPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

private const val WEATHER_PREFERENCES = "weather_preferences.pb"
private const val SETTINGS_PREFERENCES = "setting_preferences"

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Singleton
    @Provides
    fun provideWeatherDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            produceFile = {
                appContext.dataStoreFile(SETTINGS_PREFERENCES)
            }
        )
    }

    @Singleton
    @Provides
    fun provideProtoDataStore(@ApplicationContext appContext: Context): DataStore<WeatherPreferences> {
        return DataStoreFactory.create(
            serializer = WeatherPreferencesSerializer,
            produceFile = { appContext.dataStoreFile(WEATHER_PREFERENCES) },
            corruptionHandler = null,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
        )
    }
}