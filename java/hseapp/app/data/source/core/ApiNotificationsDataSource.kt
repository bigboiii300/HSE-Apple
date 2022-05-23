package hseapp.app.data.source.core

import hseapp.app.data.objects.Notification

/**
 * скелет датасорса уведомлений
 *
 * @see hseapp.app.data.source.impl.FakeApiNotificationsDataSource
 */
abstract class ApiNotificationsDataSource {
    
    abstract suspend fun getNotifications(
        onSuccess: (List<Notification>) -> Unit
    )
}