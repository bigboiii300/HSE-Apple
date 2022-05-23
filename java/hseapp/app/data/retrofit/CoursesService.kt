package hseapp.app.data.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CoursesService {

    @GET("api/user/getCourse")
    fun getCourses(@Query("accessToken") accessToken: String) : Call<String>
}