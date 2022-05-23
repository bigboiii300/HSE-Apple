package hseapp.app.data.api

import hseapp.app.data.preferences.Prefs
import hseapp.app.data.retrofit.CoursesService
import hseapp.app.data.serialization.auth.Course
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object ApiCoursesHelper : KoinComponent {

    private val coursesService: CoursesService by inject()

    fun getCourses(
        onSuccess: (List<Course>) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                coursesService.getCourses(Prefs.accessToken).execute().let {
                    if (it.isSuccessful) {
                        val json = Json { ignoreUnknownKeys = true }
                        val courses = json.decodeFromString<List<Course>>(it.body()!!)
                        onSuccess(courses)
                    }
                }
            }.exceptionOrNull()?.printStackTrace()
        }
    }
}