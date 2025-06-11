package net.rjanda.casestudy.nba.core.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.rjanda.casestudy.nba.R

/**
 * Simple basic screen for error state.
 */
@Composable
fun ErrorScreenContent(
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,) {
            Text(stringResource(R.string.error_state))
            onRetry?.let {
                Button(onClick = onRetry) {
                    Text(stringResource(R.string.label_retry))
                }
            }
        }
    }
}
