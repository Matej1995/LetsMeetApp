package cz.sandera.letsmeet.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import cz.sandera.letsmeet.data.local.entity.CurrentWeatherDataEntity
import cz.sandera.letsmeet.data.local.entity.HourlyWeatherDataEntity

@Database(
    entities = [CurrentWeatherDataEntity::class, HourlyWeatherDataEntity::class],
    version = 1,
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract val dao: WeatherDao

    companion object {
        const val WEATHER_DATABASE_NAME = "weather_database"
    }
}