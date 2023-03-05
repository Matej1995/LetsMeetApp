package cz.sandera.letsmeet.domain.weather

data class WeatherInfo(
    val weatherData: Map<Int, List<WeatherData>>,
    val currentWeatherData: WeatherData?,
)
