package cz.sandera.letsmeet.presentation.weather.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.sandera.letsmeet.R
import cz.sandera.letsmeet.data.dataStore.WeatherPreferences
import cz.sandera.letsmeet.domain.weather.WeatherData
import cz.sandera.letsmeet.presentation.util.convertCelsiusToFahrenheit
import cz.sandera.letsmeet.presentation.util.format
import cz.sandera.letsmeet.presentation.weather.WeatherViewModel
import java.time.format.DateTimeFormatter

private const val TIME_FORMAT = "HH:mm"
private const val TEMP_FORMAT_DECIMAL_ROUNDED = 2

@Composable
fun HourlyWeatherDisplay(
    weatherData: WeatherData,
    viewModel: WeatherViewModel,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White
) {
    val appSettings = viewModel.appSetting.collectAsState(
        initial = WeatherPreferences.getDefaultInstance()
    ).value

    val formattedTime = remember(weatherData) {
        weatherData.time.format(
            DateTimeFormatter.ofPattern(TIME_FORMAT)
        )
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = formattedTime,
            color = Color.LightGray
        )
        Image(
            painter = painterResource(id = weatherData.weatherType.iconRes),
            contentDescription = null,
            modifier = Modifier.width(40.dp)
        )
        Text(
            text = if (appSettings.tempUnit == WeatherPreferences.TemperatureUnit.Celsius)
                stringResource(
                    id = R.string.tempUnit_celsius,
                    weatherData.temperatureCelsius
                ) else
                stringResource(
                    id = R.string.tempUnit_fahrenheit,
                    weatherData.temperatureCelsius.convertCelsiusToFahrenheit()
                        .format(TEMP_FORMAT_DECIMAL_ROUNDED)
                ),
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}