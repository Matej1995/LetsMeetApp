package cz.sandera.letsmeet.data.mappers

import cz.sandera.letsmeet.data.local.entity.CurrentWeatherDataEntity
import cz.sandera.letsmeet.data.local.entity.HourlyWeatherDataEntity
import cz.sandera.letsmeet.data.remote.dto.WeatherDataDto
import cz.sandera.letsmeet.data.remote.dto.WeatherDto
import cz.sandera.letsmeet.domain.weather.WeatherData
import cz.sandera.letsmeet.domain.weather.WeatherInfo
import cz.sandera.letsmeet.domain.weather.WeatherType
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

private const val HOURS_PER_DAY = 24

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData,
)

fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidities[index]
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode),
            )
        )
    }.groupBy {
        it.index / HOURS_PER_DAY
    }.mapValues {
        it.value.map {
            it.data
        }
    }.also {
        println(it)
    }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentData = weatherDataMap[0]?.find { weatherData ->
        val hour = if (now.minute < 30) now.hour else now.hour + 1
        weatherData.time.hour == hour
    }

    return WeatherInfo(
        weatherData = weatherDataMap,
        currentWeatherData = currentData
    )
}

fun WeatherInfo.toCurrentWeatherEntity(): CurrentWeatherDataEntity {
    val data = currentWeatherData

    return CurrentWeatherDataEntity(
        time = data?.time?.atZone(ZoneId.systemDefault())?.toInstant()
            ?.toEpochMilli()!!,
        temperatureCelsius = data.temperatureCelsius,
        pressure = data.pressure,
        windSpeed = data.windSpeed,
        humidity = data.humidity,
        weatherType = WeatherType.toWMO(data.weatherType),
    )
}

fun WeatherInfo.toHourlyWeatherEntity(): List<HourlyWeatherDataEntity> {
    val data = weatherData.values.flatten()

    val hourlyList = data.map { data ->
        HourlyWeatherDataEntity(
            currentWeatherId = 1L,
            time = data.time.atZone(ZoneId.systemDefault())?.toInstant()
                ?.toEpochMilli()!!,
            temperatureCelsius = data.temperatureCelsius,
            pressure = data.pressure,
            windSpeed = data.windSpeed,
            humidity = data.humidity,
            weatherType = WeatherType.toWMO(data.weatherType),
        )
    }

    return hourlyList
}

fun List<HourlyWeatherDataEntity>.toWeatherData(): Map<Int, List<WeatherData>> {
    val weatherData = this.mapIndexed { index, entity ->
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(entity.time),
                    TimeZone.getDefault().toZoneId()
                ),
                temperatureCelsius = entity.temperatureCelsius,
                pressure = entity.pressure,
                windSpeed = entity.windSpeed,
                humidity = entity.humidity,
                weatherType = WeatherType.fromWMO(entity.weatherType)
            )
        )
    }.groupBy {
        it.index / HOURS_PER_DAY
    }.mapValues {
        it.value.map {
            it.data
        }
    }

    return weatherData
}

fun CurrentWeatherDataEntity.toWeatherData(): WeatherData {
    val data = this
    return WeatherData(
        time = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(data.time),
            TimeZone.getDefault().toZoneId()
        ),
        temperatureCelsius = data.temperatureCelsius,
        pressure = data.pressure,
        windSpeed = data.windSpeed,
        humidity = data.humidity,
        weatherType = WeatherType.fromWMO(data.weatherType)
    )
}