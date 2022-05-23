package hseapp.app.ui.screens.main.user

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.google.accompanist.placeholder.PlaceholderDefaults
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.material.shimmerHighlightColor
import com.google.accompanist.placeholder.placeholder
import com.ramcosta.composedestinations.annotation.Destination
import hseapp.app.LocalActivity
import hseapp.app.R
import hseapp.app.utils.compose.HSETypography
import hseapp.app.utils.compose.LocalHSEColors

/**
 * экран текущего пользователя
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@Destination
fun UserScreen(
//    navigator: DestinationsNavigator,
    viewModel: UserViewModel = viewModel()
) {
    val account = viewModel.account.collectAsState()
    val photo = viewModel.getPhoto()

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
                    text = "Профиль",
                    style = HSETypography.h5.copy(color = LocalHSEColors.current.textLabel),
                    modifier = Modifier
                        .padding(start = 16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    ) {
        val imageUri = remember { mutableStateOf<Uri?>(null) } // UPDATE

        val launcher =
            rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                imageUri.value = uri
                viewModel.updatePhoto(uri)
            }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    AsyncImage(
                        model = imageUri.value ?: photo.toUri(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = account.value?.let {
                                "${it.lastName} ${it.firstName} ${it.patronymic} "
                            }.toString(),
                            style = HSETypography.h5.copy(color = LocalHSEColors.current.textLabel),
                            modifier = Modifier
                                .placeholder(
                                    visible = account.value == null,
                                    highlight = PlaceholderHighlight.shimmer(),
                                    color = PlaceholderDefaults.shimmerHighlightColor()
                                )
                                .clip(RoundedCornerShape(4.dp))
                                .animateContentSize()
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Роль: " +
                                    if (account.value?.isTeacher == true) "Преподаватель" else "Студент",
                            style = HSETypography.body1.copy(color = LocalHSEColors.current.textLabel),
                            modifier = Modifier
                                .placeholder(
                                    visible = account.value == null,
                                    highlight = PlaceholderHighlight.shimmer(),
                                    color = PlaceholderDefaults.shimmerHighlightColor()
                                )
                                .clip(RoundedCornerShape(4.dp))
                                .animateContentSize()
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = account.value?.email.toString(),
                            style = HSETypography.body1.copy(color = LocalHSEColors.current.textBody),
                            modifier = Modifier
                                .placeholder(
                                    visible = account.value == null,
                                    highlight = PlaceholderHighlight.shimmer(),
                                    color = PlaceholderDefaults.shimmerHighlightColor()
                                )
                                .clip(RoundedCornerShape(4.dp))
                                .animateContentSize()
                        )
                    }
                }
            }
            item {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 4.dp)
                        .padding(horizontal = 16.dp),
                    color = LocalHSEColors.current.textFieldBackground
                )
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 56.dp)
                        .clickable {
                            launcher.launch("image/*")
                        }
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_upload),
                        contentDescription = null,
                        modifier = Modifier.size(28.dp),
                        tint = LocalHSEColors.current.primary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Обновить фотографию",
                        style = HSETypography.body1.copy(color = LocalHSEColors.current.textLabel),
                    )
                }
            }
            item {
                val activity = LocalActivity.current
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 56.dp)
                        .clickable {
                            viewModel.logOut {
                                activity.recreate()
                            }
                        }
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_logout),
                        contentDescription = null,
                        modifier = Modifier.size(28.dp),
                        tint = LocalHSEColors.current.negative
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Выйти",
                        style = HSETypography.body1.copy(color = LocalHSEColors.current.textLabel),
                    )
                }
            }
        }
    }
}