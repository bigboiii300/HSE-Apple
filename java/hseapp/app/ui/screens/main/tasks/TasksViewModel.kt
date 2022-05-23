package hseapp.app.ui.screens.main.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hseapp.app.data.objects.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * вьюмодель экрана задач
 */
class TasksViewModel : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>?>(null)
    val tasks = _tasks.asStateFlow()

    init {
        //"грузим" список задач, надо потом будет вынести в датасорс и бд
        viewModelScope.launch {
            _tasks.emit(
                listOf(
                    Task(
                        id = 1,
                        name = "Лабораторная работа №1",
                        deadline = 1652780874000,
                        image = "https://maza.by/wp-content/uploads/2015/06/1379704416_587198622.png",
                        author = "Сосновский Григорий Михайлович\n"
                    ),
                    Task(
                        id = 2,
                        name = "Лабораторная работа №2",
                        deadline = 1652780874000,
                        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS7nWGqvjRGxKT25-Ux-JHblTj9O4-IrQiCTEJhezgjaI-2PlzJ0nUJ6hjBr4egZ1vEEL0&usqp=CAU",
                        author = "Сосновский Григорий Михайлович\n"
                    ),
                    Task(
                        id = 3,
                        name = "Лабораторная работа №3",
                        deadline = 1652780874000,
                        image = "https://memepedia.ru/wp-content/uploads/2019/06/lobanov-mem-shablon.jpg",
                        author = "Сосновский Григорий Михайлович\n"
                    ),
                )
            )
        }
    }
}