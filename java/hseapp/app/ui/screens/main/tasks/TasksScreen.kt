package hseapp.app.ui.screens.main.tasks

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hseapp.app.R
import hseapp.app.data.objects.Task
import hseapp.app.utils.compose.HSETypography
import hseapp.app.utils.compose.LocalHSEColors
import java.util.*

/**
 * экран задач
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@Destination
fun TasksScreen(
    navigator: DestinationsNavigator,
    viewModel: TasksViewModel = viewModel()
) {
    val tasks = viewModel.tasks.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = LocalHSEColors.current.background,
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = WindowInsets.statusBars.asPaddingValues(),
                backgroundColor = LocalHSEColors.current.background,
                elevation = 0.dp
            ) {
                IconButton(
                    onClick = {
                        navigator.popBackStack()
                    },
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back_android),
                        contentDescription = null,
                        tint = LocalHSEColors.current.primary
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Задания",
                    style = HSETypography.h5.copy(color = LocalHSEColors.current.textLabel),
                    textAlign = TextAlign.Start
                )
            }
        }
    ) {
        when {
            tasks.value != null && tasks.value!!.isNotEmpty() -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = it
                ) {
                    for (i in tasks.value!!) {
                        item {
                            TaskCard(task = i)
                        }
                    }
                }
            }
            tasks.value != null && tasks.value!!.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Нет заданий",
                        style = HSETypography.h5.copy(LocalHSEColors.current.textLabel)
                    )
                }
            }
            else -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(56.dp),
                        color = LocalHSEColors.current.primary
                    )
                }
            }
        }
    }
}

/**
 * карточка задачи
 */
@Composable
private fun TaskCard(task: Task) {
    Card(
        modifier = Modifier
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
            .fillMaxWidth(),
        backgroundColor = LocalHSEColors.current.background,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = task.image,
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = task.name,
                    style = HSETypography.h6.copy(color = LocalHSEColors.current.textLabel)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Дедлайн: " + SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                        .format(Date(task.deadline)),
                    style = HSETypography.body1.copy(color = LocalHSEColors.current.textLabel)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Автор: ${task.author}",
                    style = HSETypography.body1.copy(color = LocalHSEColors.current.textLabel)
                )
            }
        }
    }
}