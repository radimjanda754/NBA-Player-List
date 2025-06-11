package net.rjanda.casestudy.nba.player.screen.team

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.rjanda.casestudy.nba.R
import net.rjanda.casestudy.nba.core.model.data.ContentState
import net.rjanda.casestudy.nba.core.screen.EmptyScreenContent
import net.rjanda.casestudy.nba.core.screen.ErrorScreenContent
import net.rjanda.casestudy.nba.core.screen.LoadingScreenContent
import net.rjanda.casestudy.nba.player.model.data.Player
import net.rjanda.casestudy.nba.player.screen.detail.DetailInfo
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun PlayerTeamScreen(
    playerId: Int,
    navigateBack: () -> Unit,
) {
    val viewModel = koinViewModel<PlayerTeamViewModel> { parametersOf(playerId) }

    val teamState by viewModel.state.collectAsStateWithLifecycle()

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

        when (teamState) {
            is ContentState.Error -> {
                ErrorScreenContent(Modifier.fillMaxSize())
            }

            is ContentState.Loading -> {
                LoadingScreenContent(Modifier.fillMaxSize())
            }

            is ContentState.Success -> with(teamState as ContentState.Success<Player.Team>) {
                PlayerDetails(
                    data,
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
    team: Player.Team,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .verticalScroll(rememberScrollState())
    ) {
        SelectionContainer {
            Column(Modifier.padding(12.dp)) {
                Text(team.fullName, style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(6.dp))

                DetailInfo(stringResource(R.string.label_conference), team.conference)
                DetailInfo(stringResource(R.string.label_division), team.division)
                DetailInfo(stringResource(R.string.label_city), team.city)
                DetailInfo(stringResource(R.string.label_name), team.name)
                DetailInfo(stringResource(R.string.label_full_name), team.fullName)
                DetailInfo(stringResource(R.string.label_abbreviation), team.abbreviation)
            }
        }
    }
}
