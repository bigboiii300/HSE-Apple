package hseapp.app.data.objects

/**
 * sealed class - класс, для которого заранее точно известны все его наследники
 */
sealed class Notification {

    /**
     * уведомление типа новое сообщение
     */
    class NewMessage(
        val from: User, //кто прислал сообщение
        val messageText: String //текст сообщения
    ) : Notification()

    /**
     * уведомление типа новый дедлайн
     */
    class Deadline(
        val date: Long, //на какую дату дедлайн, в unix
        val professor: User, //кто поставил дедлайн
        val info: String //описание
    ) : Notification()

    /**
     * уведомление типа добавления на курс
     */
    class AddingToCourse(
        val whoDid: User, //кто добавил на курс
        val name: String //название курса
    ) : Notification()
}
