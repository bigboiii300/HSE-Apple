package hseapp.app.data.serialization.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestEmailBody(
    @SerialName("email") val email: String,
    @SerialName("code") val code: String
)
