package cz.sandera.letsmeet.presentation.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.sandera.letsmeet.R
import cz.sandera.letsmeet.domain.model.SettingsType
import cz.sandera.letsmeet.presentation.setting.components.SettingTabRow
import cz.sandera.letsmeet.presentation.util.UiEvent
import cz.sandera.letsmeet.ui.theme.DarkBlue
import cz.sandera.letsmeet.ui.theme.DeepBlue
import cz.sandera.letsmeet.ui.theme.LightBlue
import cz.sandera.letsmeet.ui.theme.spacing

@Destination
@Composable
fun SettingScreen(
    navigator: DestinationsNavigator,
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

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                UiEvent.NavigateUp -> navigator.navigateUp()
                else -> Unit
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBlue)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = modifier
                .padding(MaterialTheme.spacing.medium)
                .clickable {
                    viewModel.onEvent(SettingsEvent.NavigateUp)
                }
        )
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