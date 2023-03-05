package cz.sandera.letsmeet.data.local.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import cz.sandera.letsmeet.data.local.entity.CurrentWeatherDataEntity
import cz.sandera.letsmeet.data.local.entity.HourlyWeatherDataEntity

data class CurrentWeatherWithHourlyForecast(
    @Embedded val currentWeatherData: CurrentWeatherDataEntity,
    @Relation(
        parentColumn = "currentWeatherId",
        entityColumn = "currentWeatherId"
    )
    val hourlyWeather: List<HourlyWeatherDataEntity>

)