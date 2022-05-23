package hseapp.app.ui.screens.main.user

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hseapp.app.data.api.ApiParticipantHelper
import hseapp.app.data.db.HSEDatabase
import hseapp.app.data.db.entities.ACCOUNT_TYPE_ASSISTANT
import hseapp.app.data.db.entities.ACCOUNT_TYPE_PROFESSOR
import hseapp.app.data.db.entities.ACCOUNT_TYPE_STUDENT
import hseapp.app.data.objects.Account
import hseapp.app.data.objects.Role
import hseapp.app.data.objects.User
import hseapp.app.data.preferences.Prefs
import hseapp.app.data.serialization.auth.Profile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * вьюмодель экрана текущего пользователя
 */
class UserViewModel : ViewModel(), KoinComponent {
    
    private val database: HSEDatabase by inject()
    
    private val _account = MutableStateFlow<Profile?>(null)
    val account get() = _account.asStateFlow()

    init {
        //грузим данные об аккаунте
        ApiParticipantHelper.getProfile {
            viewModelScope.launch { _account.emit(it) }
        }
    }

    /**
     * обновляем фотку пользователя
     *
     * правда держаться она будет ровно до того момента, пока мы не перезайдем в приложение.
     * есть 2 решения:
     *  - запрашивать доступ к хранилищу
     *  - грузить фотку на сервер
     */
    fun updatePhoto(uri: Uri?) {
        uri?.let {
            Prefs.profilePhoto = it.toString()
        }
    }

    fun getPhoto() = Prefs.profilePhoto

    //разлогин
    fun logOut(onLogOut: () -> Unit) {
        Prefs.accessToken = ""
        onLogOut()
    }
}