import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

/**
 * Simple loading effect for images
 * @param isVisible Determines if the effect should be currently visible.
 */
fun Modifier.shimmer(isVisible: Boolean): Modifier = composed {
    if (isVisible) {
        val transition = rememberInfiniteTransition()
        val size = remember { mutableStateOf(IntSize.Zero) }
        val shimmerTranslate = transition.animateFloat(
            initialValue = 0f,
            targetValue = 1.5f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )

        val shimmerBrush = Brush.linearGradient(
            colors = listOf(
                Color.LightGray.copy(alpha = 0.8f),
                Color.Gray.copy(alpha = 0.8f),
                Color.LightGray.copy(alpha = 0.8f)
            ),
            start = Offset.Zero,
            end = Offset(
                x = size.value.height.toFloat() * shimmerTranslate.value,
                y = size.value.width.toFloat() * shimmerTranslate.value
            )
        )

        this.background(shimmerBrush).onGloballyPositioned {
            size.value = it.size
        }
    } else {
        this
    }
}