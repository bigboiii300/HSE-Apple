package hseapp.app.data.source.core

import hseapp.app.data.objects.Message

/**
 * скелет датасорса сообщения
 *
 * @see hseapp.app.data.source.impl.FakeApiMessagesDataSource
 */
abstract class ApiMessagesDataSource {

    abstract suspend fun getMessages(
        forChatId: Int,
        onSuccess: (List<Message>) -> Unit
    )

    abstract suspend fun sendMessage(
        forChatId: Int,
        message: Message
    )
}