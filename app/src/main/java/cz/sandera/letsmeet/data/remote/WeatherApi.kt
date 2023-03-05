package cz.sandera.letsmeet.data.remote

import cz.sandera.letsmeet.data.remote.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/forecast?hourly=temperature_2m,weathercode,relativehumidity_2m,windspeed_10m,pressure_msl")
    suspend fun getCurrentWeatherByGeoLocation(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double,
    ): WeatherDto
}