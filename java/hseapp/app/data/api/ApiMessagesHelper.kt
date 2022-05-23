package hseapp.app.data.api

import hseapp.app.data.objects.Message
import hseapp.app.data.source.core.ApiMessagesDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * утилита для простого получения сообщений, просто дергает датасорс
 */
object ApiMessagesHelper : KoinComponent {

    /**
     * датасорс
     */
    private val messagesDataSource: ApiMessagesDataSource by inject()

    /**
     * функция для получения сообщений
     *
     * @param forChatId чат для которого необходимо получить сообщения
     * @param onSuccess коллбек с результатом запроса
     */
    fun getMessages(forChatId: Int, onSuccess: (List<Message>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            messagesDataSource.getMessages(forChatId, onSuccess)
        }
    }

    /**
     * функция для отправки сообщений
     *
     * @param forChatId чат в который необходимо отправить сообщение
     * @param message сообщение которое необходимо отправить
     */
    fun sendMessage(forChatId: Int, message: Message) {
        CoroutineScope(Dispatchers.IO).launch {
            messagesDataSource.sendMessage(forChatId, message)
        }
    }
}