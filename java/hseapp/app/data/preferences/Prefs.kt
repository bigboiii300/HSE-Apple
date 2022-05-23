package hseapp.app.data.preferences

import splitties.preferences.Preferences

/**
 * Удобная оберка для префов
 */
object Prefs : Preferences("hse_prefs") {

    /**
     * Определяет запущено приложение в первый раз или нет, нужно для создания фейковых данных
     * при старте приложения
     *
     * @see hseapp.app.utils.FakeDataCreator
     */
    var launchedFirstTime by boolPref("first_launch", true)

    /**
     * определяет идентификатор аккаунта в который вошел пользователь, иначе -1
     */
    var selectedAccountId by intPref("selected_account", -1)

    /**
     * супер безопасный вариант хранить токен доступа
     */
    var accessToken by stringPref("access_token", "")

    /**
     * типа сервер для хранения фотки
     */
    var profilePhoto by stringPref("photo", "https://inlnk.ru/kXmKjY")
}