package hseapp.app.ui.kit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import hseapp.app.utils.compose.LocalHSEColors

/**
 * нижняя навигация с поддержкой прозрачного навбара
 */
@Composable
fun FixedBottomNavigation(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
    contentColor: Color = contentColorFor(backgroundColor),
    content: @Composable RowScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clipToBounds()
            .then(modifier)
    ) {
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = LocalHSEColors.current.textFieldStroke
        )
        CompositionLocalProvider(
            LocalContentColor provides contentColor
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectableGroup(),
                horizontalArrangement = Arrangement.SpaceBetween,
                content = content
            )
        }
    }
}