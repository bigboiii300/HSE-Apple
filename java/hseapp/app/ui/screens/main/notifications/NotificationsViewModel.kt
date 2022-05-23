package hseapp.app.ui.screens.main.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hseapp.app.data.objects.Notification
import hseapp.app.data.source.core.ApiNotificationsDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * вьюмодель экрана уведомлений
 */
class NotificationsViewModel : ViewModel(), KoinComponent {
    
    private val apiNotificationsDataSource: ApiNotificationsDataSource by inject()

    val list = MutableStateFlow<List<Notification>?>(null)
    
    init {
        //грузим
        viewModelScope.launch {
            apiNotificationsDataSource.getNotifications {
                viewModelScope.launch {
                    list.emit(it)
                }
            }
        }
    }
}