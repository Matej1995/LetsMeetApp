package cz.sandera.letsmeet.presentation.setting

import cz.sandera.letsmeet.domain.model.SettingsType

sealed interface SettingsEvent {
    data class SaveUserSettings(val settingsType: SettingsType, val newValue: Int) : SettingsEvent
}