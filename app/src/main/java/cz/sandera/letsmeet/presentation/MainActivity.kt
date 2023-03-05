package cz.sandera.letsmeet.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import cz.sandera.letsmeet.presentation.components.BottomBar
import cz.sandera.letsmeet.ui.theme.LetsMeetTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }

        setContent {
            val navController = rememberNavController()
            val scaffoldState = rememberScaffoldState()

            LetsMeetTheme {
                Scaffold(
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        BottomBar(navController)
                    },
                ) {
                    DestinationsNavHost(
                        dependenciesContainerBuilder = {
                            dependency(scaffoldState)
                        },
                        navGraph = NavGraphs.root,
                        navController = navController,
                        modifier = Modifier.padding(it)
                    )
                }
            }
        }
    }
}