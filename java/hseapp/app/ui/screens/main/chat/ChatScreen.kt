package hseapp.app.ui.screens.main.chat

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hseapp.app.R
import hseapp.app.data.objects.Message
import hseapp.app.data.preferences.Prefs
import hseapp.app.utils.compose.HSETypography
import hseapp.app.utils.compose.LocalHSEColors

/**
 * экран чата
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@Destination
fun ChatScreen(
    navigator: DestinationsNavigator,
    chatId: Int,
    chatPhoto: String,
    chatName: String,
    viewModel: ChatViewModel = viewModel()
) {
//    val replyMessage by rememberSaveable { mutableStateOf<Message?>(null) }
    var message by rememberSaveable { mutableStateOf("") }

    val messages = viewModel.messages.collectAsState()

    fun send() {
        viewModel.sendMessage(forChatId = chatId, text = message)
        message = ""
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                backgroundColor = LocalHSEColors.current.background,
                contentPadding = WindowInsets.statusBars.asPaddingValues(),
                elevation = 2.dp
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
                AsyncImage(
                    model = chatPhoto,
                    contentDescription = chatName,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = chatName,
                    style = HSETypography.h6.copy(color = LocalHSEColors.current.textLabel),
                    textAlign = TextAlign.Start
                )
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LocalHSEColors.current.background)
            ) {
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = LocalHSEColors.current.textFieldStroke
                )
                Row(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .imePadding()
                        .fillMaxWidth()
                        .animateContentSize(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    TextField(
                        value = message,
                        onValueChange = { message = it },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = LocalHSEColors.current.background,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = LocalHSEColors.current.primary,
                            placeholderColor = LocalHSEColors.current.textBody,
                            textColor = LocalHSEColors.current.textLabel
                        ),
                        modifier = Modifier.weight(1f),
                        keyboardActions = KeyboardActions(
                            onSend = { send() }
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Send
                        ),
                        placeholder = {
                            Text(text = "Сообщение...")
                        },
                        maxLines = 4
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = ::send,
                        modifier = Modifier.size(56.dp),
                        enabled = message.isNotBlank()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_send),
                            contentDescription = null,
                            tint = LocalHSEColors.current.primary.copy(
                                alpha = animateFloatAsState(
                                    targetValue = if (message.isNotBlank()) 1f else 0.5f
                                ).value
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    ) {
        Messages(list = messages.value, paddingValues = it)
    }

    LaunchedEffect(Unit) {
        viewModel.init(chatId)
    }
}

/**
 * список сообщений
 *
 * вынес в отдельную функцию чтобы каждый раз когда новый список сообщений происходила рекомпозиция,
 * иначе при новом сообщении список не прокручивается до него
 */
@Composable
private fun Messages(list: List<Message>?, paddingValues: PaddingValues) {
    val state = rememberLazyListState()

    when {
        list != null && list.isNotEmpty() -> {
            LazyColumn(
                contentPadding = PaddingValues(
                    start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                    end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
                    top = paddingValues.calculateTopPadding() + 8.dp,
                    bottom = paddingValues.calculateBottomPadding() + 8.dp
                ),
                state = state,
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                for (i in list) {
                    item {
                        Message(message = i)
                    }
                }
            }

            LaunchedEffect(list) {
                state.animateScrollToItem(list.lastIndex)
            }
        }
        list != null && list.isEmpty() -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Нет сообщений",
                    style = HSETypography.body1.copy(LocalHSEColors.current.textLabel)
                )
            }
        }
        else -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = LocalHSEColors.current.primary
                )
            }
        }
    }
}

/**
 * вьюшка сообщения
 */
@Composable
private fun Message(message: Message/*, onReplied: () -> Unit*/) {
    val fromCurrentUser = message.author.id == Prefs.selectedAccountId

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = if (fromCurrentUser) Arrangement.End else Arrangement.Start
    ) {
        if (!fromCurrentUser) {
            AsyncImage(
                model = message.author.photoUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Column(
            modifier = Modifier
                .padding(
                    end = if (fromCurrentUser) 0.dp else 96.dp,
                    start = if (fromCurrentUser) 96.dp else 0.dp
                )
                .background(
                    color = if (!fromCurrentUser)
                        LocalHSEColors.current.textFieldBackground
                    else LocalHSEColors.current.primary,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
        ) {
            if (!fromCurrentUser) {
                Text(
                    text = "${message.author.lastName} ${message.author.firstName}",
                    style = HSETypography.body1.copy(
                        color = LocalHSEColors.current.primary,
                        fontWeight = FontWeight.Medium
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
            }
            if (message.reply != null) {
                Row {
                    val dp = LocalDensity.current.density
                    val height = remember { mutableStateOf(0.dp) }

                    Box(
                        modifier = Modifier
                            .padding(vertical = 2.dp)
                            .width(2.dp)
                            .height(height.value)
                            .background(
                                if (fromCurrentUser) LocalHSEColors.current.onPrimary else LocalHSEColors.current.primary,
                                CircleShape
                            )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        modifier = Modifier.onGloballyPositioned {
                            height.value = (it.size.height / dp).dp - 4.dp
                        }
                    ) {
                        Text(
                            text = "${message.reply.author.lastName} ${message.reply.author.firstName}",
                            style = HSETypography.body2.copy(
                                color = if (fromCurrentUser) LocalHSEColors.current.onPrimary else LocalHSEColors.current.primary,
                                fontWeight = FontWeight.Medium
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = message.reply.text,
                            style = HSETypography.body2.copy(
                                color = if (fromCurrentUser) LocalHSEColors.current.onPrimary else LocalHSEColors.current.textLabel
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Text(
                text = message.text,
                style = HSETypography.body1.copy(
                    color = if (fromCurrentUser) LocalHSEColors.current.onPrimary else LocalHSEColors.current.textLabel
                )
            )
        }
    }

// попытки сделать swipe-to-reply
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp, horizontal = 16.dp)
//            .draggable(
//                orientation = Orientation.Horizontal,
//                state = rememberDraggableState {
//                    if (offset.value + it.roundToInt() >= 0)
//                        offset.value += it.roundToInt()
//                    else offset.value = 0
//                },
//                onDragStopped = {
//                    println(it)
//                }
//            )
//    ) {
//        Box(
//            modifier = Modifier
//                .size(72.dp)
//                .background(LocalHSEColors.current.primary)
//                .offset { IntOffset(offset.value, 0) }
//        )
//    }
}