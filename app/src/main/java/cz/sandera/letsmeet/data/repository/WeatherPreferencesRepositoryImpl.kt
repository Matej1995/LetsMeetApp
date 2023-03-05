package cz.sandera.letsmeet.data.repository

import androidx.datastore.core.DataStore
import cz.sandera.letsmeet.data.dataStore.WeatherPreferences
import cz.sandera.letsmeet.data.dataStore.WeatherPreferences.TemperatureUnit
import cz.sandera.letsmeet.domain.repository.WeatherPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException
import javax.inject.Inject

class WeatherPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<WeatherPreferences>
) : WeatherPreferencesRepository {

    override val weatherPreference: Flow<WeatherPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(WeatherPreferences.getDefaultInstance())
            } else {
                throw exception
            }
        }

    override suspend fun updateTempUnit(tempUnit: TemperatureUnit) {
        try {
            dataStore.updateData { currentPrefs ->
                currentPrefs.toBuilder().setTempUnit(tempUnit).build()
            }
        } catch (e: IOException) {
            // Handle error
        }
    }

    override suspend fun updateWindUnit(windUnit: WeatherPreferences.WindUnit) {
        try {
            dataStore.updateData { currentPrefs ->
                currentPrefs.toBuilder().setWindUnit(windUnit).build()
            }
        } catch (e: IOException) {
            // Handle error
        }
    }
}