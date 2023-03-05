package cz.sandera.letsmeet.presentation.weather.components

import androidx.compose.foundation.Image

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.sandera.letsmeet.R
import cz.sandera.letsmeet.data.dataStore.WeatherPreferences
import cz.sandera.letsmeet.presentation.components.WeatherDataDisplay
import cz.sandera.letsmeet.presentation.util.convertCelsiusToFahrenheit
import cz.sandera.letsmeet.presentation.util.convertWindKilometersToMeters
import cz.sandera.letsmeet.presentation.util.format
import cz.sandera.letsmeet.presentation.weather.WeatherState
import cz.sandera.letsmeet.presentation.weather.WeatherViewModel
import cz.sandera.letsmeet.ui.theme.spacing
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

private const val TIME_FORMAT = "HH:mm"
private const val TEMP_FORMAT_DECIMAL_ROUNDED = 2

@Composable
fun WeatherCard(
    state: WeatherState,
    backgroundColor: Color,
    viewModel: WeatherViewModel,
    modifier: Modifier = Modifier
) {
    val appSettings = viewModel.appSetting.collectAsState(
        initial = WeatherPreferences.getDefaultInstance()
    ).value

    state.weatherInfo?.currentWeatherData?.let { data ->
        Card(
            backgroundColor = backgroundColor,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier.padding(MaterialTheme.spacing.medium),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.medium),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(
                        id = R.string.forecast_dayWithHour, data.time.format(
                            DateTimeFormatter.ofPattern(TIME_FORMAT)
                        )
                    ),
                    modifier = Modifier.align(Alignment.End),
                    color = Color.White,
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                Image(
                    painter = painterResource(id = data.weatherType.iconRes),
                    contentDescription = null,
                    modifier = Modifier.width(100.dp)
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                Text(
                    text = if (appSettings.tempUnit == WeatherPreferences.TemperatureUnit.Celsius)
                        stringResource(
                            id = R.string.tempUnit_celsius,
                            data.temperatureCelsius
                        ) else
                        stringResource(
                            id = R.string.tempUnit_fahrenheit,
                            data.temperatureCelsius.convertCelsiusToFahrenheit()
                                .format(TEMP_FORMAT_DECIMAL_ROUNDED)
                        ),
                    fontSize = 50.sp,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                Text(
                    text = data.weatherType.weatherDesc,
                    fontSize = 20.sp,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherDataDisplay(
                        value = data.pressure.roundToInt(),
                        unit = stringResource(id = R.string.pressure_unit),
                        icon = ImageVector.vectorResource(id = R.drawable.ic_pressure),
                        iconTine = Color.White,
                        textStyle = TextStyle(color = Color.White)
                    )
                    WeatherDataDisplay(
                        value = data.humidity.roundToInt(),
                        unit = stringResource(id = R.string.humidity_unit),
                        icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
                        iconTine = Color.White,
                        textStyle = TextStyle(color = Color.White)
                    )
                    WeatherDataDisplay(
                        value = if (appSettings.windUnit == WeatherPreferences.WindUnit.Kilometers)
                            data.windSpeed.roundToInt()
                            else data.windSpeed.convertWindKilometersToMeters().roundToInt(),
                        unit = if (appSettings.windUnit == WeatherPreferences.WindUnit.Kilometers)
                            stringResource(id = R.string.wind_unit_km)
                            else stringResource(id = R.string.wind_unit_m),
                        icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
                        iconTine = Color.White,
                        textStyle = TextStyle(color = Color.White)
                    )
                }
            }
        }
    }
}