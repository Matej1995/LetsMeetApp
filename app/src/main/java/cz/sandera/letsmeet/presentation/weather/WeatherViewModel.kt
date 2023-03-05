package cz.sandera.letsmeet.presentation.weather

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.sandera.letsmeet.R
import cz.sandera.letsmeet.data.connectivity.NetworkConnectivityObserver
import cz.sandera.letsmeet.data.permission.PermissionHandlerImpl
import cz.sandera.letsmeet.domain.location.LocationTracker
import cz.sandera.letsmeet.domain.permission.PermissionHandler
import cz.sandera.letsmeet.domain.repository.WeatherPreferencesRepository
import cz.sandera.letsmeet.domain.repository.WeatherRepository
import cz.sandera.letsmeet.domain.util.ConnectivityObserver
import cz.sandera.letsmeet.domain.util.Resource
import cz.sandera.letsmeet.domain.weather.WeatherData
import cz.sandera.letsmeet.presentation.util.UiEvent
import cz.sandera.letsmeet.presentation.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

private const val HOURS_PER_DAY = 24

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker,
    private val weatherSettings: WeatherPreferencesRepository,
    private val networkConnectivityObserver: NetworkConnectivityObserver,
) : ViewModel(), PermissionHandler by PermissionHandlerImpl() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var appSetting = weatherSettings.weatherPreference

    var state by mutableStateOf(WeatherState())
        private set

    fun loadWeatherInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(
                isLoading = true,
                error = null,
            )
            locationTracker.getCurrentLocation()?.let { location ->
                when (val result =
                    repository.getCurrentWeather(location.latitude, location.longitude)) {
                    is Resource.Success -> {
                        state = state.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null,
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            error = result.message,
                            isLoading = false,
                            weatherInfo = null,
                        )
                    }
                }
            } ?: kotlin.run {
                state = state.copy(
                    isLoading = false,
                    error = "Couldnt retrive location. Make sure to grant permission and enable GPS."
                )
            }
        }
    }

    fun observeNetworkConnectivity() {
        viewModelScope.launch {
            networkConnectivityObserver.observe().collect { networkStatus ->
                when (networkStatus) {
                    ConnectivityObserver.Status.Losing, ConnectivityObserver.Status.Lost, ConnectivityObserver.Status.Unavailable -> {
                        _uiEvent.send(
                            UiEvent.ShowSnackbar(
                                UiText.StringResource(R.string.networkConnection_error)
                            )
                        )
                    }
                    else -> Unit
                }
            }
        }
    }

    fun getHourlyForecast(weatherData: Map<Int, List<WeatherData>>): List<WeatherData> {
        val list = weatherData.values.flatten().filter { data ->
            data.time > LocalDateTime.now().minusHours(1)
        }.take(HOURS_PER_DAY)

        return list
    }
}