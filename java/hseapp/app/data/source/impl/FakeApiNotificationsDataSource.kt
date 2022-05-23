package hseapp.app.data.source.impl

import hseapp.app.data.db.HSEDatabase
import hseapp.app.data.db.entities.NOTIFICATION_TYPE_ADDING_TO_COURSE
import hseapp.app.data.db.entities.NOTIFICATION_TYPE_DEADLINE
import hseapp.app.data.db.entities.NOTIFICATION_TYPE_MESSAGE
import hseapp.app.data.objects.Notification
import hseapp.app.data.objects.Role
import hseapp.app.data.objects.User
import hseapp.app.data.source.core.ApiNotificationsDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.random.Random

/**
 * фейковая реализация датасорса уведомлений
 */
class FakeApiNotificationsDataSource : ApiNotificationsDataSource(), KoinComponent {

    /**
     * инжектим базу данных
     *
     * @see hseapp.app.data.di.trashModule
     */
    private val database: HSEDatabase by inject()

    /**
     * получаем уведомления, просто запрос в бд и конвертация в нормальные модельки
     */
    override suspend fun getNotifications(
        onSuccess: (List<Notification>) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            val dbResult = database.getNotificationsDao().getAll()
            val result = mutableListOf<Notification>()
            dbResult.forEach {
                when(it.type) {
                    NOTIFICATION_TYPE_MESSAGE -> {
                        result.add(
                            Notification.NewMessage(
                                from = User(
                                    id = it.userId,
                                    firstName = it.fullName.split(' ')[1],
                                    lastName = it.fullName.split(' ')[0],
                                    patronymic = it.fullName.split(' ')[2],
                                    photoUrl = it.photo,
                                    role = Role.STUDENT
                                ),
                                messageText = it.info
                            )
                        )
                    }
                    NOTIFICATION_TYPE_DEADLINE -> {
                        result.add(
                            Notification.Deadline(
                                date = it.date!!,
                                professor = User(
                                    id = it.userId,
                                    firstName = it.fullName.split(' ')[1],
                                    lastName = it.fullName.split(' ')[0],
                                    patronymic = it.fullName.split(' ')[2],
                                    photoUrl = it.photo,
                                    role = Role.PROFESSOR
                                ),
                                info = it.info
                            )
                        )
                    }
                    NOTIFICATION_TYPE_ADDING_TO_COURSE -> {
                        result.add(
                            Notification.AddingToCourse(
                                whoDid = User(
                                    id = it.userId,
                                    firstName = it.fullName.split(' ')[1],
                                    lastName = it.fullName.split(' ')[0],
                                    patronymic = it.fullName.split(' ')[2],
                                    photoUrl = it.photo,
                                    role = Role.PROFESSOR
                                ),
                                name = it.info
                            )
                        )
                    }
                }
            }
            delay(Random.nextLong(200, 1000)) //рандомная задержка для иллюзии загрузки
            onSuccess(result)
        }
    }
}