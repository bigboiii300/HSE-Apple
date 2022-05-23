package hseapp.app.ui.screens.main.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hseapp.app.R
import hseapp.app.ui.screens.destinations.CoursesScreenDestination
import hseapp.app.ui.screens.destinations.MessagesScreenDestination
import hseapp.app.ui.screens.destinations.TasksScreenDestination
import hseapp.app.utils.compose.HSETypography
import hseapp.app.utils.compose.LocalHSEColors

/**
 * экран дашборда
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@Destination
fun DashboardScreen(
    navigator: DestinationsNavigator
) {
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
                Text(
                    text = "Дашборд",
                    style = HSETypography.h5.copy(color = LocalHSEColors.current.textLabel),
                    modifier = Modifier
                        .padding(start = 16.dp),
//                        .weight(1f),
                    textAlign = TextAlign.Start
                )
//                IconButton(
//                    onClick = { },
//                    modifier = Modifier.size(56.dp)
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_add),
//                        contentDescription = null,
//                        tint = LocalHSEColors.current.primary
//                    )
//                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 56.dp)
                        .clickable {
                            navigator.navigate(MessagesScreenDestination)
                        }
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_message),
                        contentDescription = null,
                        modifier = Modifier.size(28.dp),
                        tint = LocalHSEColors.current.primary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Сообщения",
                        style = HSETypography.body1.copy(color = LocalHSEColors.current.textLabel),
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 56.dp)
                        .clickable {
                            navigator.navigate(CoursesScreenDestination)
                        }
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_brain),
                        contentDescription = null,
                        modifier = Modifier.size(28.dp),
                        tint = LocalHSEColors.current.primary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Курсы",
                        style = HSETypography.body1.copy(color = LocalHSEColors.current.textLabel),
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 56.dp)
                        .clickable {
                            navigator.navigate(TasksScreenDestination)
                        }
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_education),
                        contentDescription = null,
                        modifier = Modifier.size(28.dp),
                        tint = LocalHSEColors.current.primary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Задания",
                        style = HSETypography.body1.copy(color = LocalHSEColors.current.textLabel),
                    )
                }
            }
        }
    }
}