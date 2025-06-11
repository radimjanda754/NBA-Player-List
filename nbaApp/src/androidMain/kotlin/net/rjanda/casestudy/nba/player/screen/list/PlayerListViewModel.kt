package net.rjanda.casestudy.nba.player.screen.list

import kotlinx.coroutines.flow.combine
import net.rjanda.casestudy.nba.core.BaseViewModel
import net.rjanda.casestudy.nba.player.repository.PlayerRepository

class PlayerListViewModel(private val playerRepository: PlayerRepository) : BaseViewModel<PlayerListViewState>() {

    init {
        combine(playerRepository.observePlayers(), playerRepository.observePlayersPaging()) { players, paging ->
            PlayerListViewState(playerList = players, hasNextPage = paging.isLoadingOrSuccess())
        }.observeInState()
        refresh(true)
    }

    fun refresh(setLoading: Boolean) {
        launchInState(setLoading = setLoading) {
            playerRepository.refreshPlayers()
        }
    }

    fun loadNextPlayers() {
        launchInState {
            playerRepository.loadNextPlayers()
        }
    }

}
