package hseapp.app.ui.screens.main.courses

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hseapp.app.R
import hseapp.app.data.serialization.auth.Course
import hseapp.app.utils.compose.HSETypography
import hseapp.app.utils.compose.LocalHSEColors

/**
 * Экран курсов
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@Destination
fun CoursesScreen(
    navigator: DestinationsNavigator,
    viewModel: CoursesViewModel = viewModel()
) {
    val courses = viewModel.courses.collectAsState()

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
                    text = "Курсы",
                    style = HSETypography.h5.copy(color = LocalHSEColors.current.textLabel),
                    textAlign = TextAlign.Start
                )
            }
        }
    ) {
        when {
            courses.value != null && courses.value!!.isNotEmpty() -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = it
                ) {
                    for (i in courses.value!!) {
                        item {
                            CourseCard(course = i)
                        }
                    }
                }
            }
            courses.value != null && courses.value!!.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Нет курсов",
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
 * карточка курсов
 */
@Composable
private fun CourseCard(course: Course) {
    val joined = rememberSaveable { mutableStateOf(false) }

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
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://www.meme-arsenal.com/memes/42574eb547602b12d68ffb819f7baada.jpg",
                contentDescription = null,
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = course.name,
                    style = HSETypography.h6.copy(color = LocalHSEColors.current.textLabel)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Кто ведет:",
                    style = HSETypography.body1.copy(color = LocalHSEColors.current.textLabel)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = course.creatorName,
                    style = HSETypography.body1.copy(color = LocalHSEColors.current.textLabel)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = if (joined.value) "Заявка отправлена" else "Отправить заявку",
                    style = HSETypography.h6.copy(
                        color = LocalHSEColors.current.primary,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable(enabled = !joined.value) {
                            joined.value = true
                        }
                        .alpha(if (joined.value) 0.5f else 1f)
                )
            }
        }
    }
}