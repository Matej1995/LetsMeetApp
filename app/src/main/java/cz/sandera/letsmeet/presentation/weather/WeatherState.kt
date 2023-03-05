package cz.sandera.letsmeet.presentation.weather

import cz.sandera.letsmeet.domain.weather.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
