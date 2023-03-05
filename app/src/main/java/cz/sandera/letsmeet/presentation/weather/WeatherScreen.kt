package cz.sandera.letsmeet.presentation.weather

import android.Manifest
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.sandera.letsmeet.presentation.components.PermissionDialog
import cz.sandera.letsmeet.presentation.destinations.SettingScreenDestination
import cz.sandera.letsmeet.presentation.util.LocationPermissionTextProvider
import cz.sandera.letsmeet.presentation.util.UiEvent
import cz.sandera.letsmeet.presentation.util.openAppSettings
import cz.sandera.letsmeet.presentation.weather.components.WeatherCard
import cz.sandera.letsmeet.presentation.weather.components.WeatherForecast
import cz.sandera.letsmeet.ui.theme.DarkBlue
import cz.sandera.letsmeet.ui.theme.DeepBlue
import cz.sandera.letsmeet.ui.theme.spacing
import kotlinx.coroutines.flow.collect

@RootNavGraph(start = true)
@Destination
@Composable
fun WeatherScreen(
    scaffoldState: ScaffoldState,
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = hiltViewModel(),
) {
    val activity = LocalContext.current as Activity
    val context = LocalContext.current
    val dialogQueue = viewModel.visiblePermissionDialogQueue

    val permissionToRequest = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
    )

    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            permissionToRequest.forEach { permission ->
                viewModel.onPermissionResult(
                    permission = permission,
                    isGranted = perms[permission] == true
                )
            }
            viewModel.loadWeatherInfo()
        }
    )

    LaunchedEffect(true) {
        // Check network connection
        viewModel.observeNetworkConnectivity()

        // Check permission
        multiplePermissionResultLauncher.launch(permissionToRequest)

        if (viewModel.state.weatherInfo == null) {
            viewModel.loadWeatherInfo()
        }

        // One time event
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context),
                        duration = SnackbarDuration.Long,
                    )
                }
                else -> Unit
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(DarkBlue)
                .verticalScroll(rememberScrollState())
        ) {
            WeatherCard(
                state = viewModel.state,
                backgroundColor = DeepBlue,
                viewModel = viewModel
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            WeatherForecast(
                viewModel = viewModel,
                forecastHourly = viewModel.state.weatherInfo?.weatherData?.let { weatherData ->
                    viewModel.getHourlyForecast(weatherData)
                }
            )
        }
        if (viewModel.state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
        viewModel.state.error?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    dialogQueue
        .reversed()
        .forEach { permission ->
            PermissionDialog(
                permissionTextProvider = when (permission) {
                    Manifest.permission.ACCESS_FINE_LOCATION -> {
                        LocationPermissionTextProvider()
                    }
                    Manifest.permission.ACCESS_COARSE_LOCATION -> {
                        LocationPermissionTextProvider()
                    }
                    else -> return@forEach
                },
                isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                    activity, permission
                ),
                onDismiss = viewModel::dismissDialog,
                onSuccessClick = {
                    viewModel.dismissDialog()
                    multiplePermissionResultLauncher.launch(
                        arrayOf(permission)
                    )
                },
                onGoToAppSettingsClick = {
                    activity.openAppSettings()
                }
            )
        }
}