package cz.sandera.letsmeet.presentation.setting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.sandera.letsmeet.data.dataStore.WeatherPreferences
import cz.sandera.letsmeet.domain.model.SettingsType
import cz.sandera.letsmeet.domain.repository.WeatherPreferencesRepository
import cz.sandera.letsmeet.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val weatherPrefs: WeatherPreferencesRepository,
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(SettingsState())
        private set

    init {
        viewModelScope.launch {
            weatherPrefs.weatherPreference.collect { weatherPrefs ->
                state = state.copy(
                    tempSelected = weatherPrefs.tempUnitValue,
                    windSelected = weatherPrefs.windUnitValue,
                )
            }
        }
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.SaveUserSettings -> {
                viewModelScope.launch {
                    when (event.settingsType) {
                        SettingsType.WIND -> {
                            weatherPrefs.updateWindUnit(
                                WeatherPreferences.WindUnit.forNumber(
                                    event.newValue
                                )
                            )
                            state = state.copy(
                                windSelected = event.newValue
                            )
                        }
                        SettingsType.TEMP -> {
                            weatherPrefs.updateTempUnit(
                                WeatherPreferences.TemperatureUnit.forNumber(
                                    event.newValue
                                )
                            )
                            state = state.copy(
                                tempSelected = event.newValue
                            )
                        }
                    }
                }
            }
            SettingsEvent.NavigateUp -> {
                _uiEvent.trySend(UiEvent.NavigateUp)
            }
        }
    }
}