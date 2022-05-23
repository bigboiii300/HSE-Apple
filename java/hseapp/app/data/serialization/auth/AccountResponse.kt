package hseapp.app.data.serialization.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountResponse(
    @SerialName("accessToken") val accessToken: String
)
