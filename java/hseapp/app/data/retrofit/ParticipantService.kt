package hseapp.app.data.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ParticipantService {

    @GET("api/user/profile")
    fun getProfile(@Query("accessToken") accessToken: String): Call<String>
}