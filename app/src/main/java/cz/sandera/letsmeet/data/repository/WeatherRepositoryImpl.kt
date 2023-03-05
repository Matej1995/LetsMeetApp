package cz.sandera.letsmeet.data.repository

import cz.sandera.letsmeet.data.local.WeatherDao
import cz.sandera.letsmeet.data.local.entity.HourlyWeatherDataEntity
import cz.sandera.letsmeet.data.mappers.toCurrentWeatherEntity
import cz.sandera.letsmeet.data.mappers.toHourlyWeatherEntity
import cz.sandera.letsmeet.data.mappers.toWeatherData
import cz.sandera.letsmeet.data.mappers.toWeatherInfo
import cz.sandera.letsmeet.data.remote.WeatherApi
import cz.sandera.letsmeet.data.remote.dto.WeatherDto
import cz.sandera.letsmeet.domain.repository.WeatherRepository
import cz.sandera.letsmeet.domain.util.Resource
import cz.sandera.letsmeet.domain.weather.WeatherData
import cz.sandera.letsmeet.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao,
) : WeatherRepository {

    override suspend fun getCurrentWeather(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = weatherApi.getCurrentWeatherByGeoLocation(
                    lat = lat,
                    long = long
                ).toWeatherInfo().also { weatherInfo ->
                    weatherDao.insertWeather(
                        currentWeatherData = weatherInfo.toCurrentWeatherEntity(),
                        hourlyForecast = weatherInfo.toHourlyWeatherEntity()
                    )
                }
            )
        } catch (e: java.lang.Exception) {
            val data = loadAllDataFromDatabase()
            data?.let {
                Resource.Success(data)
            } ?: run {
                e.printStackTrace()
                Resource.Error(e.message ?: "An unknown error occurred.")
            }
        }
    }

    override suspend fun loadAllDataFromDatabase(): WeatherInfo? {
        val result = weatherDao.getCurrentWeatherWithHourlyForecast()
        val weatherData = result.hourlyWeather.toWeatherData()

        return WeatherInfo(
            currentWeatherData = result.currentWeatherData.toWeatherData(),
            weatherData = weatherData
        ).also {
            print(it)
        }
    }
}