package hseapp.app.utils

import hseapp.app.data.db.HSEDatabase
import hseapp.app.data.db.entities.*
import hseapp.app.data.preferences.Prefs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Штука для создания фейковых данных в бд при первом запуске приложения
 */
object FakeDataCreator : KoinComponent {
    
    private val database: HSEDatabase by inject()
    
    fun createFakeData() {
        CoroutineScope(Dispatchers.IO).launch {
            database.getAccountDao().insert(
                DBAccountEntity(
                    1,
                    "Киряков Игорь Андреевич",
                    "https://inlnk.ru/kXmKjY",
                    type = ACCOUNT_TYPE_STUDENT,
                    email = "ikiryakov@edu.hse.ru"
                )
            )
            database.getNotificationsDao().insert(
                DBNotificationEntity(
                    type = NOTIFICATION_TYPE_MESSAGE,
                    info = "How is it going?",
                    userId = 2,
                    fullName = "Сосновский Григорий Михайлович",
                    photo = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTnQTUIJpSTCl6niHjr0a4_ycGW29XNNZGziqhABkdmXr5uUarUNghdyGAgsHQbpe5Ln0s&usqp=CAU"
                )
            )
            database.getNotificationsDao().insert(
                DBNotificationEntity(
                    type = NOTIFICATION_TYPE_DEADLINE,
                    info = "Домашняя работа №1",
                    date = System.currentTimeMillis(),
                    userId = 2,
                    fullName = "Сосновский Григорий Михайлович",
                    photo = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTnQTUIJpSTCl6niHjr0a4_ycGW29XNNZGziqhABkdmXr5uUarUNghdyGAgsHQbpe5Ln0s&usqp=CAU"
                )
            )
            database.getNotificationsDao().insert(
                DBNotificationEntity(
                    type = NOTIFICATION_TYPE_ADDING_TO_COURSE,
                    info = "НИС IOS Разработка",
                    userId = 2,
                    fullName = "Сосновский Григорий Михайлович",
                    photo = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTnQTUIJpSTCl6niHjr0a4_ycGW29XNNZGziqhABkdmXr5uUarUNghdyGAgsHQbpe5Ln0s&usqp=CAU"
                )
            )
            
            Prefs.launchedFirstTime = false
        }
    }
}