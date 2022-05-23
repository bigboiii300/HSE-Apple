package hseapp.app.ui.screens.main.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hseapp.app.data.api.ApiMessagesHelper
import hseapp.app.data.objects.Message
import hseapp.app.data.objects.Role
import hseapp.app.data.objects.User
import hseapp.app.data.preferences.Prefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * вьюмодель для экрана чата
 */
class ChatViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>?>(null)
    val messages = _messages.asStateFlow()

    fun init(chatId: Int) {
        //грузим сообщения для чата
        ApiMessagesHelper.getMessages(chatId) {
            viewModelScope.launch {
                _messages.emit(it)
            }
        }
    }

    fun sendMessage(
        forChatId: Int,
        text: String
    ) {
        val message = Message(
            id = -1,
            time = System.currentTimeMillis(),
            author = User(
                id = Prefs.selectedAccountId,
                firstName = "Михуил",
                lastName = "Зубенко",
                patronymic = "Педрович",
                photoUrl = "",
                role = Role.STUDENT
            ),
            reply = null,
            text = text
        )
        ApiMessagesHelper.sendMessage(
            forChatId = forChatId,
            message = message
        )
        viewModelScope.launch {
            _messages.emit(
                _messages.value?.toMutableList()?.apply { add(message) }
            )
        }
    }
}