package cz.sandera.letsmeet.domain.repository

import cz.sandera.letsmeet.data.dataStore.WeatherPreferences
import cz.sandera.letsmeet.data.dataStore.WeatherPreferences.TemperatureUnit
import cz.sandera.letsmeet.data.dataStore.WeatherPreferences.WindUnit
import kotlinx.coroutines.flow.Flow

interface WeatherPreferencesRepository {

    /**
     * Get all user preferences.
     */
    val weatherPreference: Flow<WeatherPreferences>

    /**
     * Update user preference for temp.
     */
    suspend fun updateTempUnit(tempUnit: TemperatureUnit)

    /**
     * Update user preference for wind.
     */
    suspend fun updateWindUnit(windUnit: WindUnit)

}