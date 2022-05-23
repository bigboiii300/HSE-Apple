package hseapp.app.ui.screens.main.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hseapp.app.data.api.ApiCoursesHelper
import hseapp.app.data.serialization.auth.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * вьюмодель экрана курсов
 */
class CoursesViewModel : ViewModel() {

    private val _courses = MutableStateFlow<List<Course>?>(null)
    val courses = _courses.asStateFlow()

    init {
        ApiCoursesHelper.getCourses {
            viewModelScope.launch { _courses.emit(it) }
        }
    }
}