package hseapp.app.ui.screens.main.messages

import androidx.lifecycle.ViewModel
import hseapp.app.data.objects.Conversation

/**
 * вьюмодель экрана списка чатов
 */
class MessagesViewModel : ViewModel() {

    /**
     * "загруженный" список чатов, по-хорошему надо будет тоже вынести в датасорс и бд
     */
    val chats = listOf(
        Conversation(
            id = 1,
            name = "Общий",
            photo = "https://ololo.tv/wp-content/uploads/2018/09/53e8e6b7c1231.jpeg",
            unreadCount = 0,
            lastMessage = null
//            Message(
//                id = 1,
//                time = System.currentTimeMillis(),
//                author = User(
//                    id = 3,
//                    firstName = "Aboba",
//                    lastName = "Bebra",
//                    patronymic = "Huy",
//                    photoUrl = "https://s13.stc.yc.kpcdn.net/share/i/12/5763808/wr-960.webp",
//                    role = Role.STUDENT
//                ),
//                reply = null,
//                text = "Че с лицом?"
//            )
        ),
        Conversation(
            id = 2,
            name = "Важное",
            photo = "https://ae04.alicdn.com/kf/HTB1wPQ1RAPoK1RjSZKbq6x1IXXaq/-.jpg",
            unreadCount = 0,
            lastMessage = null
//            Message(
//                id = 1,
//                time = System.currentTimeMillis(),
//                author = User(
//                    id = 3,
//                    firstName = "Aboba",
//                    lastName = "Bebra",
//                    patronymic = "Huy",
//                    photoUrl = "https://s13.stc.yc.kpcdn.net/share/i/12/5763808/wr-960.webp",
//                    role = Role.STUDENT
//                ),
//                reply = null,
//                text = "Слава Украине"
//            )
        ),
        Conversation(
            id = 3,
            name = "Преподаватели и студенты",
            photo = "https://prv1.lori-images.net/smeshnaya-sobaka-v-kostume-i-galstuke-s-belozuboi-0004824005-preview.jpg",
            unreadCount = 0,
            lastMessage = null
//          Message(
//                id = 1,
//                time = System.currentTimeMillis(),
//                author = User(
//                    id = 3,
//                    firstName = "Aboba",
//                    lastName = "Bebra",
//                    patronymic = "Huy",
//                    photoUrl = "https://s13.stc.yc.kpcdn.net/share/i/12/5763808/wr-960.webp",
//                    role = Role.STUDENT
//                ),
//                reply = null,
//                text = "Lorem ipsum"
//            )
        ),
        Conversation(
            id = 4,
            name = "Флудилка",
            photo = "https://www.meme-arsenal.com/memes/2b095142f116f18d09059d873c8dee17.jpg",
            unreadCount = 0,
            lastMessage = null
//          Message(
//                id = 1,
//                time = System.currentTimeMillis(),
//                author = User(
//                    id = 3,
//                    firstName = "Aboba",
//                    lastName = "Bebra",
//                    patronymic = "Huy",
//                    photoUrl = "https://s13.stc.yc.kpcdn.net/share/i/12/5763808/wr-960.webp",
//                    role = Role.STUDENT
//                ),
//                reply = null,
//                text = "A text which is as long text as it needs to don't have enough space to draw itself entirely"
//            )
        ),
    )
}