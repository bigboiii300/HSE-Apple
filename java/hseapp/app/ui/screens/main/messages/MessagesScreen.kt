package hseapp.app.ui.screens.main.messages

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hseapp.app.R
import hseapp.app.data.objects.Conversation
import hseapp.app.ui.screens.destinations.ChatScreenDestination
import hseapp.app.utils.compose.HSETypography
import hseapp.app.utils.compose.LocalHSEColors

/**
 * экран списка чатов
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@Destination
fun MessagesScreen(
    navigator: DestinationsNavigator,
    viewModel: MessagesViewModel = viewModel()
) {
    val chats = remember { viewModel.chats }

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
                    text = "Сообщения",
                    style = HSETypography.h5.copy(color = LocalHSEColors.current.textLabel),
                    textAlign = TextAlign.Start
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            itemsIndexed(chats) { index, item ->
                Conversation(conversation = item) {
                    navigator.navigate(
                        ChatScreenDestination(
                            chatId = item.id,
                            chatPhoto = item.photo,
                            chatName = item.name
                        )
                    )
                }
                if (index < chats.lastIndex) {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 86.dp),
                        color = LocalHSEColors.current.textFieldBackground
                    )
                }
            }
        }
    }
}

/**
 * вьюшка еденицы чата
 */
@Composable
private fun Conversation(conversation: Conversation, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 72.dp)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = conversation.photo,
            contentDescription = null,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = conversation.name,
                style = HSETypography.h6.copy(color = LocalHSEColors.current.textLabel),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            if (conversation.lastMessage != null) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = conversation.lastMessage.text,
                    style = HSETypography.body1.copy(color = LocalHSEColors.current.textBody),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
        if (conversation.unreadCount > 0) {
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .defaultMinSize(minWidth = 28.dp, minHeight = 28.dp)
                    .background(
                        color = LocalHSEColors.current.primary,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = conversation.unreadCount.toString(),
                    style = HSETypography.body1.copy(color = LocalHSEColors.current.onPrimary)
                )
            }
        }
    }
}