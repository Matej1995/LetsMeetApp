package cz.sandera.letsmeet.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class HourlyWeatherDataEntity(
    @PrimaryKey
    val hourlyWeatherId: Long? = null,
    val currentWeatherId: Long,
    val time: Long,
    val temperatureCelsius: Double,
    val pressure: Double,
    val windSpeed: Double,
    val humidity: Double,
    val weatherType: Int,
)
