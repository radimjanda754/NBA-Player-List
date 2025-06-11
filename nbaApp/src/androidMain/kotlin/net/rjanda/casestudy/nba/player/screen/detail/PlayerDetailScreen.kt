package net.rjanda.casestudy.nba.player.screen.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.rjanda.casestudy.nba.R
import net.rjanda.casestudy.nba.core.model.data.ContentState
import net.rjanda.casestudy.nba.core.screen.EmptyScreenContent
import net.rjanda.casestudy.nba.core.screen.ErrorScreenContent
import net.rjanda.casestudy.nba.core.screen.LoadingScreenContent
import net.rjanda.casestudy.nba.player.model.data.Player
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun PlayerDetailScreen(
    playerId: Int,
    navigateToTeamDetails: (playerId: Int) -> Unit,
    navigateBack: () -> Unit,
) {
    val viewModel = koinViewModel<PlayerDetailViewModel> { parametersOf(playerId) }

    val playerState by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.back))
                    }
                }
            )
        },
        modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
    ) { paddingValues ->

        when (playerState) {
            is ContentState.Error -> {
                ErrorScreenContent(Modifier.fillMaxSize())
            }

            is ContentState.Loading -> {
                LoadingScreenContent(Modifier.fillMaxSize())
            }

            is ContentState.Success -> with(playerState as ContentState.Success<Player>) {
                PlayerDetails(
                    data,
                    onShowTeam = {
                        navigateToTeamDetails(playerId)
                    },
                    modifier = Modifier.padding(paddingValues),
                )
            }

            is ContentState.Empty -> {
                EmptyScreenContent(Modifier.fillMaxSize())
            }
        }
    }

}

@Composable
private fun PlayerDetails(
    player: Player,
    onShowTeam: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .verticalScroll(rememberScrollState())
    ) {
        SelectionContainer {
            Column(Modifier.padding(12.dp)) {
                Text(player.fullName, style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(6.dp))
                
                DetailInfo(stringResource(R.string.label_position), player.position)
                DetailInfo(stringResource(R.string.label_height), player.height)
                DetailInfo(stringResource(R.string.label_weight), player.weight)
                DetailInfo(stringResource(R.string.label_jersey_number), player.jerseyNumber)
                DetailInfo(stringResource(R.string.label_college), player.college)
                DetailInfo(stringResource(R.string.label_country), player.country)
                DetailInfo(stringResource(R.string.label_team), player.team?.fullName)

                Button(
                    modifier = Modifier.padding(vertical = 8.dp),
                    onClick = {
                        onShowTeam()
                    }) {
                    Text(stringResource(R.string.label_show_team))
                }
            }
        }
    }
}

@Composable
fun DetailInfo(
    label: String,
    data: String?,
    modifier: Modifier = Modifier,
) {
    data?.let {
        Column(modifier.padding(vertical = 4.dp)) {
            Spacer(Modifier.height(6.dp))
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("$label: ")
                    }
                    append(data)
                }
            )
        }
    }
}
