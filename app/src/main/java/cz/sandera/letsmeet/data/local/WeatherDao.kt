package cz.sandera.letsmeet.data.local

import androidx.room.*
import cz.sandera.letsmeet.data.local.entity.CurrentWeatherDataEntity
import cz.sandera.letsmeet.data.local.entity.HourlyWeatherDataEntity
import cz.sandera.letsmeet.data.local.entity.relation.CurrentWeatherWithHourlyForecast

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherInfo(currentWeatherData: CurrentWeatherDataEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourlyForecast(hourlyForecast: List<HourlyWeatherDataEntity>)

    @Query("DELETE FROM currentWeatherDataEntity")
    fun deleteCurrentInfo()

    @Query("DELETE FROM hourlyWeatherDataEntity")
    fun deleteHourlyWeather()

    @Transaction
    suspend fun insertWeather(
        currentWeatherData: CurrentWeatherDataEntity,
        hourlyForecast: List<HourlyWeatherDataEntity>
    ) {
        deleteCurrentInfo()
        deleteHourlyWeather()
        insertWeatherInfo(currentWeatherData)
        insertHourlyForecast(hourlyForecast)
    }

    @Transaction
    @Query("SELECT * FROM currentWeatherDataEntity")
    fun getCurrentWeatherWithHourlyForecast(): CurrentWeatherWithHourlyForecast?
}