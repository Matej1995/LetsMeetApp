package cz.sandera.letsmeet.presentation.weather.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.sandera.letsmeet.domain.weather.WeatherData
import cz.sandera.letsmeet.ui.theme.spacing
import cz.sandera.letsmeet.R
import cz.sandera.letsmeet.presentation.weather.WeatherViewModel

@Composable
fun WeatherForecast(
    forecastHourly: List<WeatherData>?,
    viewModel: WeatherViewModel,
    modifier: Modifier = Modifier
) {
    forecastHourly?.let { data ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.medium)
        ) {
            Text(
                modifier = modifier.padding(start = MaterialTheme.spacing.small),
                text = stringResource(id = R.string.hourly_title),
                fontSize = 20.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            LazyRow(content = {
                items(data) { weatherData ->
                    HourlyWeatherDisplay(
                        weatherData = weatherData,
                        viewModel = viewModel,
                        modifier = Modifier
                            .height(100.dp)
                            .padding(horizontal = MaterialTheme.spacing.medium)
                    )
                }
            })
        }
    }
}