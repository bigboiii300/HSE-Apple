package hseapp.app.data.serialization.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    @SerialName("id") val id: Int,
    @SerialName("firstName") val firstName: String,
    @SerialName("surname") val lastName: String,
    @SerialName("patronymic") val patronymic: String,
    @SerialName("mail") val email: String,
    @SerialName("teacher") val isTeacher: Boolean
)
