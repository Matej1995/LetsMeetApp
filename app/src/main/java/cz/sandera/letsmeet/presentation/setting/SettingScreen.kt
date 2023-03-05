package cz.sandera.letsmeet.presentation.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import cz.sandera.letsmeet.R
import cz.sandera.letsmeet.domain.model.SettingsType
import cz.sandera.letsmeet.presentation.setting.components.SettingTabRow
import cz.sandera.letsmeet.ui.theme.DarkBlue
import cz.sandera.letsmeet.ui.theme.DeepBlue
import cz.sandera.letsmeet.ui.theme.LightBlue

@Destination
@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel()
) {
    val tempList = listOf(
        stringResource(id = R.string.settingsTab_tempCelsius),
        stringResource(id = R.string.settingsTab_tempFahrenheit),
    )

    val windList = listOf(
        stringResource(id = R.string.settingsTab_windKilometers),
        stringResource(id = R.string.settingsTab_windMeters),
    )

    val state = viewModel.state

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBlue)
    ) {
        SettingTabRow(
            title = R.string.settings_windTitle,
            tabBackgroundColor = DeepBlue,
            selectedColor = Color.White,
            textColor = LightBlue,
            valueIsSelected = state.windSelected,
            tabList = windList,
            onClickAction = { selectedIndex ->
                viewModel.onEvent(
                    SettingsEvent.SaveUserSettings(
                        SettingsType.WIND,
                        selectedIndex
                    )
                )
            }
        )

        SettingTabRow(
            title = R.string.setting_tempTitle,
            tabBackgroundColor = DeepBlue,
            selectedColor = Color.White,
            textColor = LightBlue,
            valueIsSelected = state.tempSelected,
            tabList = tempList,
            onClickAction = { selectedIndex ->
                viewModel.onEvent(
                    SettingsEvent.SaveUserSettings(
                        SettingsType.TEMP,
                        selectedIndex
                    )
                )
            }
        )
    }
}