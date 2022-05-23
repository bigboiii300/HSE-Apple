package hseapp.app.data.objects

/**
 * sealed class - класс, для которого заранее точно известны все его наследники
 */
sealed class AuthError {
    
    /**
     * Неправильный логин или пароль
     */
    object InvalidData : AuthError()

    /**
     * Неизвестная ошибка
     */
    object Unknown : AuthError()
    
    //something more maybe later
}
