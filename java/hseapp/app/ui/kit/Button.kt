package hseapp.app.ui.kit

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import hseapp.app.utils.compose.LocalHSEColors

/**
 * вьюшка кнопки
 */
@Composable
fun VKUIFilledButton(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .then(modifier)
            .height(48.dp)
            .background(
                color = LocalHSEColors.current.primary.copy(
                    alpha = animateFloatAsState(targetValue = if (isEnabled && !isLoading) 1f else 0.5f).value
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = LocalHSEColors.current.onPrimary),
                onClick = onClick,
                enabled = isEnabled && !isLoading,
                role = Role.Button
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(28.dp)
                    .alpha(
                        animateFloatAsState(if (isLoading) 1f else 0f).value
                    ),
                color = LocalHSEColors.current.onPrimary,
                strokeWidth = 2.dp
            )
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp)
                    .alpha(
                        animateFloatAsState(if (!isLoading) 1f else 0f).value
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                content()
            }
        }
    }
}