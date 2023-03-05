package cz.sandera.letsmeet.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class CurrentWeatherDataEntity(
    @PrimaryKey
    val currentWeatherId: Long? = null,
    val time: Long,
    val temperatureCelsius: Double,
    val pressure: Double,
    val windSpeed: Double,
    val humidity: Double,
    val weatherType: Int,
)