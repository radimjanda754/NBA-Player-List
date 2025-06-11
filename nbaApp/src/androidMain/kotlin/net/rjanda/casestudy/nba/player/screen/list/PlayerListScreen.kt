package net.rjanda.casestudy.nba.player.screen.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skydoves.landscapist.glide.GlideImage
import net.rjanda.casestudy.nba.core.model.data.ContentState
import net.rjanda.casestudy.nba.core.screen.EmptyScreenContent
import net.rjanda.casestudy.nba.core.screen.ErrorScreenContent
import net.rjanda.casestudy.nba.core.screen.LoadingScreenContent
import net.rjanda.casestudy.nba.player.model.data.Player
import org.koin.compose.viewmodel.koinViewModel
import shimmer

@Composable
fun PlayerListScreen(
    navigateToDetails: (playerId: Int) -> Unit
) {
    val viewModel = koinViewModel<PlayerListViewModel>()

    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars).padding(12.dp)) {
        when (state) {
            is ContentState.Error -> {
                ErrorScreenContent(Modifier.fillMaxSize()) {
                    viewModel.refresh(true)
                }
            }

            is ContentState.Loading -> {
                LoadingScreenContent(Modifier.fillMaxSize())
            }

            is ContentState.Success -> with(state as ContentState.Success<PlayerListViewState>) {
                if (data.playerList.isNotEmpty()) {
                    PlayerGrid(
                        players = data.playerList,
                        onPlayerClick = navigateToDetails,
                        onBottomScrolled = {
                            viewModel.loadNextPlayers()
                        },
                        hasNextPage = data.hasNextPage,
                    )
                } else {
                    EmptyScreenContent(Modifier.fillMaxSize())
                }
            }

            is ContentState.Empty -> {
                EmptyScreenContent(Modifier.fillMaxSize())
            }
        }
    }

}

@Composable
private fun PlayerGrid(
    players: List<Player>,
    onPlayerClick: (Int) -> Unit,
    onBottomScrolled: () -> Unit,
    hasNextPage: Boolean,
    modifier: Modifier = Modifier,
) {
    val gridState = rememberLazyGridState()

    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Adaptive(180.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = WindowInsets.safeDrawing.asPaddingValues(),
    ) {
        items(players, key = { it.id }) { obj ->
            PlayerFrame(
                player = obj,
                onClick = { onPlayerClick(obj.id) },
            )
        }

        if (hasNextPage) {
            repeat(2) {
                item("loading_$it") {
                    onBottomScrolled()
                    CardFrameLoading()
                }
            }
        }
    }
}

@Composable
private fun PlayerFrame(
    player: Player,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column(Modifier.padding(8.dp)) {
            GlideImage(
                imageModel = { DEFAULT_LOGO }, // Ideally use some logo from API, not fixed URL
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(Color.LightGray)
                    .shimmer(true),
            )

            Spacer(Modifier.height(2.dp))

            Text(player.fullName, style = MaterialTheme.typography.titleMedium)
            player.team?.fullName?.let { team ->
                Text(team, style = MaterialTheme.typography.bodySmall)
            }

        }
    }
}

@Composable
private fun CardFrameLoading(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier.padding(8.dp)
    ) {
        Column(
            modifier
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(Color.LightGray)
                    .shimmer(true),
            )
        }
    }
}

const val DEFAULT_LOGO = "https://www.logodesignlove.com/images/classic/nba-logo.jpg"