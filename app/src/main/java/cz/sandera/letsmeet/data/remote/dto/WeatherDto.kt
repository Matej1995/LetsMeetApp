package cz.sandera.letsmeet.data.remote.dto

import com.squareup.moshi.Json

data class WeatherDto(
    @field:Json(name = "hourly")
    val weatherData: WeatherDataDto,
)