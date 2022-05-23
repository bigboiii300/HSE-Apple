package hseapp.app.data.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthService {

    @POST("security/login")
    @Headers("Content-Type: application/json")
    fun requestCode(@Body body: String): Call<String>

    @POST("security/entry")
    @Headers("Content-Type: application/json")
    fun auth(@Body body: String): Call<String>
}