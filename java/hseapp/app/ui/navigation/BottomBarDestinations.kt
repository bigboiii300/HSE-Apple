package hseapp.app.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import hseapp.app.R
import hseapp.app.ui.screens.destinations.DashboardScreenDestination
import hseapp.app.ui.screens.destinations.NotificationScreenDestination
import hseapp.app.ui.screens.destinations.UserScreenDestination

/**
 * декларирование всех вкладок нижней навигации
 */
enum class BottomBarDestinations(
    val direction: DirectionDestinationSpec, //ссылка на экран куда ведет вкладка
    @DrawableRes val icon: Int, //ресурс иконки вкладки
    @StringRes val label: Int //ресурс строки названия вкладки
) {
    
    Notifications(
        NotificationScreenDestination,
        R.drawable.ic_notification,
        R.string.notifications_tab
    ),
    Dashboard(
        DashboardScreenDestination,
        R.drawable.ic_dashboard,
        R.string.dashboard_tab
    ),
    User(
        UserScreenDestination,
        R.drawable.ic_profile,
        R.string.user_tab
    )
}