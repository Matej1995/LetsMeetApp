package cz.sandera.letsmeet.presentation.components

import androidx.annotation.StringRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.navigation.navigateTo
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import cz.sandera.letsmeet.R
import cz.sandera.letsmeet.presentation.NavGraphs
import cz.sandera.letsmeet.presentation.appCurrentDestinationAsState
import cz.sandera.letsmeet.presentation.destinations.Destination
import cz.sandera.letsmeet.presentation.destinations.DirectionDestination
import cz.sandera.letsmeet.presentation.destinations.SettingScreenDestination
import cz.sandera.letsmeet.presentation.destinations.WeatherScreenDestination
import cz.sandera.letsmeet.presentation.startAppDestination

enum class BottomBarDestination(
    val direction: DirectionDestination,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    Weather(WeatherScreenDestination, Icons.Default.Home, R.string.bottomBar_weather),
    Setting(SettingScreenDestination, Icons.Default.Settings, R.string.bottomBar_setting),
}

@Composable
fun BottomBar(
    navController: NavController
) {
    val currentDestination: Destination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination

    BottomNavigation {
        BottomBarDestination.values().forEach { destination ->
            BottomNavigationItem(
                selected = currentDestination == destination.direction,
                onClick = {
                    navController.navigate(destination.direction.route) {
                        launchSingleTop = true
                    }
                },
                icon = { Icon(destination.icon, contentDescription = stringResource(destination.label))},
                label = { Text(stringResource(destination.label)) },
            )
        }
    }
}