package hseapp.app.data.source.impl

import hseapp.app.data.db.HSEDatabase
import hseapp.app.data.db.entities.DBMessageEntity
import hseapp.app.data.objects.Message
import hseapp.app.data.objects.Role
import hseapp.app.data.objects.User
import hseapp.app.data.source.core.ApiMessagesDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * фейковая реализация датасорса сообщений
 */
class FakeApiMessagesDataSource : ApiMessagesDataSource(), KoinComponent {

    /**
     * инжектим базу данных
     *
     * @see hseapp.app.data.di.trashModule
     */
    private val database: HSEDatabase by inject()

    /**
     * получение сообщений, просто запрос в бд и конвертация в нормальные модельки
     */
    override suspend fun getMessages(forChatId: Int, onSuccess: (List<Message>) -> Unit) {
        withContext(Dispatchers.IO) {
            val dbResult = database.getMessagesDao().getForChat(forChatId)
            val result = mutableListOf<Message>()
            dbResult.forEach {
                result.add(
                    Message(
                        id = it.id,
                        time = it.time,
                        author = User(
                            id = it.authorId,
                            firstName = it.authorFullName.split(' ')[1],
                            lastName = it.authorFullName.split(' ')[0],
                            patronymic = it.authorFullName.split(' ')[2],
                            photoUrl = it.authorPhoto,
                            role = Role.STUDENT
                        ),
                        reply = if (it.replyMessageId != null) {
                            Message(
                                id = it.replyMessageId,
                                time = -1,
                                author = User(
                                    id = -1,
                                    firstName = it.replyMessageAuthorFullName!!.split(' ')[1],
                                    lastName = it.replyMessageAuthorFullName.split(' ')[0],
                                    patronymic = it.replyMessageAuthorFullName.split(' ')[2],
                                    photoUrl = "",
                                    role = Role.STUDENT
                                ),
                                reply = null,
                                text = it.replyMessageText!!
                            )
                        } else null,
                        text = it.text
                    )
                )
            }
            onSuccess(result)
        }
    }

    /**
     * конвертация в модельку бд и сохранение отправленного сообщения
     */
    override suspend fun sendMessage(forChatId: Int, message: Message) {
        withContext(Dispatchers.IO) {
            database.getMessagesDao().insert(
                DBMessageEntity(
                    chatId = forChatId,
                    time = System.currentTimeMillis(),
                    authorFullName = "${message.author.lastName} ${message.author.firstName} ${message.author.patronymic}",
                    authorId = message.author.id,
                    authorPhoto = message.author.photoUrl,
                    replyMessageId = message.reply?.id,
                    replyMessageAuthorFullName = if (message.reply == null) null else {
                        "${message.reply.author.lastName} ${message.reply.author.firstName} ${message.reply.author.patronymic}"
                    },
                    replyMessageText = message.reply?.text,
                    text = message.text
                )
            )
        }
    }
}