package cz.sandera.letsmeet.domain.repository

import cz.sandera.letsmeet.domain.util.Resource
import cz.sandera.letsmeet.domain.weather.WeatherInfo

interface WeatherRepository {

    /**
     * Get current weather for specific lat and long.
     */
    suspend fun getCurrentWeather(lat: Double, long: Double): Resource<WeatherInfo>

    /**
     * Load weather data from database.
     */
    suspend fun loadAllDataFromDatabase(): WeatherInfo?

}