package hseapp.app.data.api

import hseapp.app.data.preferences.Prefs
import hseapp.app.data.retrofit.ParticipantService
import hseapp.app.data.serialization.auth.Profile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object ApiParticipantHelper : KoinComponent {

    private val participantService: ParticipantService by inject()

    fun getProfile(
        onSuccess: (Profile) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                participantService.getProfile(Prefs.accessToken).execute().let {
                    if (it.isSuccessful) {
                        val json = Json { ignoreUnknownKeys = true }
                        val profile = json.decodeFromString<Profile>(it.body()!!)
                        onSuccess(profile)
                    }
                }
            }
        }
    }
}