package hseapp.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.navigateTo
import hseapp.app.data.preferences.Prefs
import hseapp.app.ui.kit.FixedBottomNavigation
import hseapp.app.ui.navigation.BottomBarDestinations
import hseapp.app.ui.screens.NavGraphs
import hseapp.app.ui.screens.appCurrentDestinationAsState
import hseapp.app.ui.screens.destinations.*
import hseapp.app.ui.screens.startAppDestination
import hseapp.app.utils.compose.HSETheme
import hseapp.app.utils.compose.LocalHSEColors

/**
 * Главная активити приложения, просто запускает разные штуки
 */
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HSETheme {
                CompositionLocalProvider(LocalActivity provides this) {
                    val systemUiController = rememberSystemUiController()
                    val isSystemInDarkTheme = isSystemInDarkTheme()

                    if (Prefs.accessToken.isEmpty()) {
                        DestinationsNavHost(
                            navGraph = NavGraphs.root,
                            startRoute = AuthLoginScreenDestination
                        )
                    } else {
                        val navController = rememberNavController()
                        val currentDestination =
                            navController.appCurrentDestinationAsState().value
                                ?: NavGraphs.root.startAppDestination

                        Scaffold(
                            bottomBar = if (currentDestination == ChatScreenDestination) {
                                {}
                            } else {
                                {
                                    FixedBottomNavigation(
                                        modifier = Modifier.navigationBarsPadding(),
                                        backgroundColor = LocalHSEColors.current.background
                                    ) {
                                        BottomBarDestinations.values().forEach { destination ->
                                            BottomNavigationItem(
                                                selected = when (destination.direction) {
                                                    BottomBarDestinations.Notifications.direction ->
                                                        currentDestination == BottomBarDestinations.Notifications.direction
                                                    BottomBarDestinations.Dashboard.direction ->
                                                        currentDestination == DashboardScreenDestination
                                                                || currentDestination == MessagesScreenDestination
                                                                || currentDestination == ChatScreenDestination
                                                                || currentDestination == TasksScreenDestination
                                                                || currentDestination == CoursesScreenDestination
                                                    BottomBarDestinations.User.direction ->
                                                        currentDestination == BottomBarDestinations.User.direction
                                                    else -> error("Unknown bottom navigation tab")
                                                },
                                                onClick = {
                                                    navController.navigateTo(destination.direction) {
                                                        launchSingleTop = true
                                                    }
                                                },
                                                icon = {
                                                    Icon(
                                                        painterResource(destination.icon),
                                                        stringResource(destination.label)
                                                    )
                                                },
                                                selectedContentColor = LocalHSEColors.current.primary,
                                                unselectedContentColor = LocalHSEColors.current.textBody
                                            )
                                        }
                                    }
                                }
                            }
                        ) {
                            DestinationsNavHost(
                                navGraph = NavGraphs.root,
                                startRoute = NotificationScreenDestination,
                                navController = navController
                            )
                        }
                    }

                    SideEffect {
                        systemUiController.setSystemBarsColor(
                            color = Color.Transparent,
                            darkIcons = !isSystemInDarkTheme
                        )
                    }
                }
            }
        }
//        Thread.setDefaultUncaughtExceptionHandler(AutoRestartCrashHandler(this))
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}

val LocalActivity =
    compositionLocalOf<ComponentActivity> { error("LocalActivity provides nothing") }