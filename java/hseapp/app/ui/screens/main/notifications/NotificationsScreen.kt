package hseapp.app.ui.screens.main.notifications

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import hseapp.app.R
import hseapp.app.data.objects.Notification
import hseapp.app.utils.compose.HSETypography
import hseapp.app.utils.compose.LocalHSEColors
import java.util.*

/**
 * экран уведомлений
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
@Destination
fun NotificationScreen(
//    navigator: DestinationsNavigator,
    viewModel: NotificationsViewModel = viewModel()
) {
    val list = viewModel.list.collectAsState()

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
                    text = "Уведомления",
                    style = HSETypography.h5.copy(color = LocalHSEColors.current.textLabel),
                    modifier = Modifier
                        .padding(start = 16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    ) {
        val listState = rememberLazyListState()

        AnimatedContent(list.value) { value ->
            when {
                value == null -> {
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
                value.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Нет уведомлений",
                            style = HSETypography.h5.copy(LocalHSEColors.current.textLabel)
                        )
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentPadding = it,
                        state = listState
                    ) {
                        itemsIndexed(value) { index, notification ->
                            val shown = rememberSaveable { mutableStateOf(true) }
                            SwipeToDismiss(
                                state = rememberDismissState(confirmStateChange = {
                                    shown.value = false
                                    true
                                }),
                                background = {}
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .animateContentSize()
                                        .then(
                                            if (shown.value) Modifier
                                            else Modifier.height(0.dp)
                                        )
                                ) {
                                    Notification(notification)
                                    if (index < value.lastIndex) {
                                        Divider(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 56.dp),
                                            color = LocalHSEColors.current.textFieldBackground
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * вьюшка еденицы уведомления
 */
@Composable
private fun Notification(notification: Notification) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .padding(14.dp)
        ) {
            Image(
                painter = painterResource(
                    when (notification) {
                        is Notification.NewMessage -> R.drawable.ic_message
                        is Notification.Deadline -> R.drawable.ic_clock
                        is Notification.AddingToCourse -> R.drawable.ic_education
                    }
                ),
                contentDescription = null,
                colorFilter = ColorFilter.tint(LocalHSEColors.current.primary)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .padding(end = 16.dp)
        ) {
            Text(
                text = when (notification) {
                    is Notification.NewMessage -> "${notification.from.firstName} ${notification.from.lastName} отправил(-а) Вам сообщение"
                    is Notification.Deadline -> "${notification.professor.lastName} ${notification.professor.firstName} " +
                            "${notification.professor.patronymic} обозначил (-а) новый дедлайн " +
                            SimpleDateFormat("dd.MM.yyy", Locale.getDefault())
                                .format(Date(notification.date))
                    is Notification.AddingToCourse -> "${notification.whoDid.lastName} ${notification.whoDid.firstName} " +
                            "${notification.whoDid.patronymic} добавил (-а) Вас на курс «${notification.name}»"
                },
                style = HSETypography.body1.copy(
                    fontWeight = FontWeight.Medium,
                    color = LocalHSEColors.current.textLabel
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            if (notification !is Notification.AddingToCourse) {
                Text(
                    text = when (notification) {
                        is Notification.NewMessage -> notification.messageText
                        is Notification.Deadline -> notification.info
                        else -> error("че вообще...")
                    },
                    style = HSETypography.body1.copy(color = LocalHSEColors.current.textBody)
                )
            }
        }
    }
}